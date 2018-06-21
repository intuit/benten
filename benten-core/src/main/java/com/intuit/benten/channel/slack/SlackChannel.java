package com.intuit.benten.channel.slack;

import allbegray.slack.SlackClientFactory;
import allbegray.slack.rtm.Event;
import allbegray.slack.rtm.EventListener;
import allbegray.slack.rtm.ProxyServerInfo;
import allbegray.slack.rtm.SlackRealTimeMessagingClient;
import allbegray.slack.type.Authentication;
import allbegray.slack.type.User;
import allbegray.slack.webapi.SlackWebApiClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.intuit.benten.common.actionhandlers.BentenActionHandler;
import com.intuit.benten.common.actionhandlers.BentenHandlerResponse;
import com.intuit.benten.common.bentennlp.ConversationFragment;
import com.intuit.benten.common.channel.Channel;
import com.intuit.benten.common.channel.ChannelInformation;
import com.intuit.benten.common.exceptions.BentenException;
import com.intuit.benten.converters.HtmlToImageConverter;
import com.intuit.benten.common.formatters.SlackFormatter;
import com.intuit.benten.handlers.BentenConversationCatalystFactory;
import com.intuit.benten.common.bentennlp.Answer;
import com.intuit.benten.common.bentennlp.Conversation;
import com.intuit.benten.common.constants.SlackConstants;
import com.intuit.benten.common.conversationcatalysts.ConversationCatalyst;
import com.intuit.benten.nlp.NlpClient;
import com.intuit.benten.properties.BentenProxyConfig;
import com.intuit.benten.common.nlp.BentenConversation;
import com.intuit.benten.common.nlp.BentenMessage;
import com.intuit.benten.handlers.BentenActionHandlerFactory;
import com.intuit.benten.properties.SlackProperties;
import com.intuit.benten.renderers.SlackMessageRenderer;
import com.jayway.jsonpath.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.script.ScriptException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;


/**
 * @author Divakar Ungatla
 * @version 1.0
 */
@Component
public class SlackChannel implements Channel {

    private static final Logger logger = LoggerFactory.getLogger(SlackChannel.class);

    private SlackRealTimeMessagingClient slackRealTimeMessagingClient;
    private SlackWebApiClient slackWebApiClient;
    private ProxyServerInfo proxyServerInfo;

    private String botUserId;

    @Autowired
    private SlackProperties slackProperties;

    @Autowired
    private BentenProxyConfig bentenProxyConfig;

    @Autowired
    private NlpClient nlpClient;

    @Autowired
    private BentenActionHandlerFactory bentenActionHandlerFactory;

    @Autowired
    private BentenConversationCatalystFactory bentenConversationCatalystFactory;

    private ConcurrentHashMap<String,BentenConversation> catalystConversations = new ConcurrentHashMap<>();


    @PostConstruct
    public void init(){
        proxyServerInfo = null;

        if (bentenProxyConfig.isProxyEnabled()) {
            logger.info("proxy is enabled using proxy server: "+ bentenProxyConfig.getHost());
            proxyServerInfo = new ProxyServerInfo();
            proxyServerInfo
                    .setHost(bentenProxyConfig.getHost());
            proxyServerInfo
                    .setProtocol(bentenProxyConfig.getProtocol());
            proxyServerInfo
                    .setPort(bentenProxyConfig.getPort());
        }

        this.slackRealTimeMessagingClient = SlackClientFactory
                .createSlackRealTimeMessagingClient(slackProperties.getToken(), proxyServerInfo);
        this.slackRealTimeMessagingClient.connect();
        this.slackWebApiClient = SlackClientFactory
                .createWebApiClient(slackProperties.getToken(),proxyServerInfo);
        this.slackRealTimeMessagingClient
                .addListener(Event.MESSAGE, onMessageEventListener);
        this.slackRealTimeMessagingClient.addListener(Event.HELLO,helloEventListener);


        logger.info("Initialized slack client successfully");
    }

    EventListener helloEventListener = (JsonNode jsonNode) -> {
        System.out.println(jsonNode.toString());
        Authentication authentication = this.slackWebApiClient.auth();
        this.botUserId = authentication.getUser_id();
    };


    EventListener onMessageEventListener = (JsonNode jsonNode) -> {
        String type = JsonPath.read(jsonNode.toString(),"$.type");
        String subType=null;
        if(jsonNode.toString().contains("subtype")) {
             subType = JsonPath.read(jsonNode.toString(), "$.subtype");
        }
        if(!("message".equals(type)) || "file_share".equals(subType)){
            return;
        }

        boolean userExists = jsonNode.has("user");
        if(! userExists) {
            return;
        }
        if(jsonNode.get("user").asText().equals(botUserId)){
            return;
        }

        CompletableFuture.supplyAsync(() -> {
            String message = JsonPath.read(jsonNode.toString(),"$.text");
            String channel = JsonPath.read(jsonNode.toString(),"$.channel");

            if(channel!=null ){
                if(channel.startsWith("C") || channel.startsWith("G")) {
                    if (!message.contains("<@" + botUserId + ">")) {
                        return null;
                    }
                }
            }

            User user = slackWebApiClient.getUserInfo(JsonPath.read(jsonNode.toString(),"$.user"));
            SlackUser slackUser = new SlackUser();
            slackUser.setName(user.getName());
            slackUser.setId(user.getId());



            if(message.toLowerCase().equals("reset") || message.toLowerCase().equals("cancel")){
                logger.info("Received a request to reset "+user.toString()+". Clearing all contexts" );
                nlpClient.sendText(message, channel,true);
                slackRealTimeMessagingClient.sendMessage(SlackHelper.slackRTMPayload("Done! you can start a new conversation now!",channel));
                if(catalystConversations.containsKey(slackUser.getId())){
                    catalystConversations.remove(slackUser.getId());
                }

                return null;
            }

            BentenMessage bentenMessage =
                    nlpClient.sendText(message, channel);

            bentenMessage.setChannelInformation(new ChannelInformation(channel));
            bentenMessage.setChannel(this);

            String userOfInterest = SlackHelper.extractUserFromMessage(message);
            JsonElement userOfInterestParam;
            User userInMessage=null;
            if(userOfInterest!=null){
                userInMessage  = slackWebApiClient.getUserInfo(userOfInterest);
                userOfInterestParam = new JsonPrimitive(userInMessage.getName());
                bentenMessage.getParameters().put(SlackConstants.USER_OF_INTEREST,userOfInterestParam);
            }

            if(catalystConversations.containsKey(slackUser.getId())){
                logger.info("Found a conversation catalyst command for action: "+bentenMessage.getAction()+" .Entering catalyst conversation.");
                BentenConversation bentenConversation = catalystConversations.get(slackUser.getId());

                String parameters[]= bentenConversation.getConversation().nextQuestion().getValue().split(Pattern.quote(" ?"));

                Answer answer = resolveAnswer(message, bentenConversation,userInMessage);

                if(answer == null)
                    return new SlackHelperPayload(bentenConversation.getNlpBentenMessage(),slackUser,channel,true);;

                bentenConversation.getConversation().setAnswerToActiveQuestion(answer);

                JsonElement jsonElement = new JsonPrimitive(answer.getValue());
                bentenConversation.getNlpBentenMessage().getParameters().put(parameters[0],jsonElement);
                SlackHelperPayload slackHelperPayload=
                        new SlackHelperPayload(bentenConversation.getNlpBentenMessage(),slackUser,channel);
                return slackHelperPayload;
            }


            JsonElement jsonElement = new JsonPrimitive(user.getName());
            bentenMessage.getParameters().put(SlackConstants.CURRENT_USER,jsonElement);

            return new SlackHelperPayload(bentenMessage,slackUser,channel);
        }).thenAcceptAsync(slackHelperPayload -> {
            if(slackHelperPayload == null){
                // do not do anything, will handle it later
                return;
            }
            if(slackHelperPayload.isRepeateQuestion()){
                slackRealTimeMessagingClient
                        .sendMessage(
                                SlackHelper.slackRTMPayload(getNextQuestionFormat(slackHelperPayload)
                                        ,slackHelperPayload.getChannel()));
            }
            else if(!slackHelperPayload.getNlpBentenMessage().isActionComplete()
                    || !nlpClient.isActionable(slackHelperPayload.getNlpBentenMessage().getAction())) {

                slackRealTimeMessagingClient
                        .sendMessage(SlackHelper.slackRTMPayload(slackHelperPayload));
            }else{

                if(!manageCatalystConversation(slackHelperPayload)) {

                    String channel = slackHelperPayload.getChannel();
                    String action = slackHelperPayload.getNlpBentenMessage().getAction();

                    slackRealTimeMessagingClient
                            .sendMessage(SlackHelper.slackRTMPayload("I am working on it", slackHelperPayload.getChannel()));
                    BentenActionHandler bentenActionHandler = bentenActionHandlerFactory
                            .getActionHandlerMap().get(slackHelperPayload.getNlpBentenMessage().getAction());

                    if(bentenActionHandler == null) {
                        slackRealTimeMessagingClient
                                .sendMessage(SlackHelper.slackRTMPayload("Sorry! I am not capable of doing that action currently :(",slackHelperPayload.getChannel()));
                        logger.info("Unable to find an action for "+ action + " . Make sure you registered your action hadler with @ActionHandlerAnnotation" );
                        return;
                    }
                    logger.info("Handling action " +  slackHelperPayload.getNlpBentenMessage().toString());
                    BentenHandlerResponse handlerResponse =
                            bentenActionHandler.handle(slackHelperPayload.getNlpBentenMessage());


                    sendResponse(handlerResponse, channel, action);
                }
            }
        }).exceptionally(ex ->
        {   logger.error(ex.getMessage(),ex);

            String channel = JsonPath.read(jsonNode.toString(),"$.channel");
            if(ex.getCause() instanceof BentenException){
                slackRealTimeMessagingClient
                        .sendMessage(SlackHelper.slackRTMPayload(SlackMessageRenderer.errorMessage(ex.getMessage().substring(ex.getMessage().lastIndexOf(":"))).getSlackText(),channel));
            }
            return null;});
    };

    private void sendResponse(BentenHandlerResponse handlerResponse, String channel, String action) {
        if (handlerResponse.getBentenSlackResponse() != null) {
            slackWebApiClient.postMessage(BentenSlackToSlackMsgConverter.transform(handlerResponse.getBentenSlackResponse(),
                    handlerResponse.getBentenSlackResponse().getSlackText(), channel));
        }
        if (handlerResponse.getBentenHtmlResponse() != null) {

            String html = null;
            try {
                html = HtmlToImageConverter.decorateHtml(handlerResponse.getBentenHtmlResponse().getHtml());
            } catch (ScriptException e) {
                logger.info(e.getMessage(),e);
            }
            InputStream stream = HtmlToImageConverter.generateImage(html);
            slackWebApiClient
                    .uploadFile(stream, "png", action, "", "", channel);

        }
    }


    private boolean manageCatalystConversation(SlackHelperPayload slackHelperPayload) {

        if (catalystConversations.containsKey(slackHelperPayload.getCurrentUser().getId())) {

            if (!catalystConversations.get(slackHelperPayload.getCurrentUser().getId()).getConversation().isConversationComplete()) {

                slackRealTimeMessagingClient
                        .sendMessage(
                                SlackHelper.slackRTMPayload(getNextQuestionFormat(slackHelperPayload)
                                        ,slackHelperPayload.getChannel()));
                return true;
            } else {
                catalystConversations.remove(slackHelperPayload.getCurrentUser().getId());
                return false;
            }
        } else {

                if (bentenConversationCatalystFactory.getConversationCatalystMap().containsKey(slackHelperPayload.getNlpBentenMessage().getAction())) {

                    ConversationCatalyst catalyst = bentenConversationCatalystFactory.getConversationCatalystMap().get(slackHelperPayload.getNlpBentenMessage().getAction());
                    Conversation conversation = catalyst.getConversation(slackHelperPayload.getNlpBentenMessage());
                    BentenConversation bentenConversation = new BentenConversation(conversation, slackHelperPayload.getNlpBentenMessage());

                    if(conversation.nextQuestion() == null)
                        return false;

                    catalystConversations.put(slackHelperPayload.getCurrentUser().getId(), bentenConversation);

                    slackRealTimeMessagingClient
                            .sendMessage(SlackHelper.slackRTMPayload(getNextQuestionFormat(slackHelperPayload),slackHelperPayload.getChannel()));
                    return true;
                 }
                 else{
                    return false;
                }
        }
    }

    private String getNextQuestionFormat(SlackHelperPayload slackHelperPayload) {
        ConversationFragment conversationFragment =
                catalystConversations.get(slackHelperPayload.getCurrentUser().getId()).getConversation().nextConversationFragment();
        SlackFormatter slackFormatter = SlackFormatter.create().text(conversationFragment.getQuestion().getValue());
        if(conversationFragment.getAllowedValues()!=null && conversationFragment.getAllowedValues().size()>0){
            slackFormatter.newline();
            for (Map.Entry<Integer, String> entry : conversationFragment.getAllowedValues().entrySet()) {
                Integer optionValue= entry.getKey()+1;
                slackFormatter.text(optionValue.toString()).code(entry.getValue()).text(" ");
            }
        }
        return slackFormatter.build();
    }

    private Answer resolveAnswer(String message, BentenConversation bentenConversation, User userInMessage) {

        Answer answer = null;

        ConversationFragment conversationFragment =
                bentenConversation.getConversation().nextConversationFragment();

        if(conversationFragment.getAllowedValues()!=null && conversationFragment.getAllowedValues().size()>0) {
            if(conversationFragment.getAllowedValues().containsKey(Integer.parseInt(message)-1)){
                answer = new Answer();
                String ans = conversationFragment.getAllowedValues().get(Integer.parseInt(message)-1);
                answer.setValue(ans);
                return answer;
            }else{
                return answer;
            }
        }

        answer = new Answer();

        if(userInMessage!=null) {
            answer.setValue(userInMessage.getName());
        }
        else{
            answer.setValue(message);
        }

        return answer;
    }


    public SlackRealTimeMessagingClient getSlackRealTimeMessagingClient() {
        return slackRealTimeMessagingClient;
    }

    @Override
    public void sendMessage(BentenHandlerResponse bentenHandlerResponse, ChannelInformation channelInformation) {
        sendResponse(bentenHandlerResponse,channelInformation.getChannelId(),"");
    }
}
