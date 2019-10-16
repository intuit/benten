package com.intuit.benten.nlp.dialogflow;

import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.AIServiceContext;
import ai.api.AIServiceContextBuilder;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import com.intuit.benten.common.nlp.BentenMessage;
import com.intuit.benten.nlp.NlpClient;
import com.intuit.benten.exceptions.AiException;
import com.intuit.benten.properties.BentenProxyConfig;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */

public class DialogFlowClient implements NlpClient {


    private AIConfiguration configuration;
    private AIDataService aiDataService ;


    public DialogFlowClient(String token, BentenProxyConfig bentenProxyConfig) {
        configuration =
                new AIConfiguration(token);
        if (bentenProxyConfig.isProxyEnabled()) {
            SocketAddress socketAddress =
                    new InetSocketAddress(bentenProxyConfig.getHost(), bentenProxyConfig.getPort());
            Proxy proxy =
                    new Proxy(Proxy.Type.valueOf(bentenProxyConfig.getProtocol().toUpperCase()), socketAddress);
            configuration.setProxy(proxy);
        }
        aiDataService =
                new AIDataService(configuration);
    }


    @Override
    public BentenMessage sendText(String text, String sessionId) throws AiException {
        return sendText(text,sessionId,false);
    }

    @Override
    public BentenMessage sendText(String text, String sessionId, boolean reset) throws AiException {
        AIServiceContext aiServiceContext =
                AIServiceContextBuilder.buildFromSessionId(sessionId);

        AIRequest request =
                new AIRequest(text);

        AIResponse response;
        BentenMessage bentenMessage;
        try {
            if(reset){
                aiDataService.resetActiveContexts(aiServiceContext);
            }

            response = aiDataService.request(request, aiServiceContext);

            bentenMessage = new BentenMessage();
            bentenMessage.setSpeech(response.getResult().getFulfillment().getSpeech());
            bentenMessage.setAction(response.getResult().getAction());
            bentenMessage.setIsActionComplete(!response.getResult().isActionIncomplete());
            bentenMessage.setParameters(response.getResult().getParameters());

        } catch (Exception e) {
            throw new AiException(e.getMessage());
        }
        return bentenMessage;
    }

    @Override
    public boolean isActionable(String action) {
        if(action.contains(DialogFlowActions.ACTION_HELP)
                || action.contains(DialogFlowActions.ACTION_INPUT_UNKOWN)
                || action.contains(DialogFlowActions.ACTION_SMALL_TALK)
                || action.contains(DialogFlowActions.ACTION_WELCOME)){
            return false;
        }
        return true;
    }
}
