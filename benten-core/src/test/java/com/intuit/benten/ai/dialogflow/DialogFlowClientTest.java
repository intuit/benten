package com.intuit.benten.ai.dialogflow;

//import BentenMessage;
import com.intuit.benten.nlp.NlpClient;
import com.intuit.benten.nlp.dialogflow.DialogFlowClient;
import com.intuit.benten.hackernews.properties.BentenProxyConfig;
import com.intuit.benten.hackernews.properties.AiProperties;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={NlpClient.class, BentenProxyConfig.class, AiProperties.class})
public class DialogFlowClientTest {

    private NlpClient nlpClient;

    @Autowired
    private BentenProxyConfig bentenProxyConfig;

    @Before
    public void setup() {
        nlpClient = new DialogFlowClient("89a1e02eacac4aa09646c93f5410e72f",bentenProxyConfig);
    }

    @Test
    public void sendAMessageToNlpClient()  {
//        BentenMessage bentenMessage = nlpClient.sendText("Book a ticket from Delhi to Bangalore on feb 20 th", "test");
//       // Assert.assertEquals(bentenMessage.getAction(),"smalltalk.greetings.hello");
//        Assert.assertEquals(bentenMessage.isActionComplete(),true);
    }

 //   @Test(expected = AiException.class)
    public void sendAMessageToNlpClientFalseAuth()  {
//        NlpClient nlpClient = new DialogFlowClient("invalid-token",bentenProxyConfig);
//        BentenMessage bentenMessage = nlpClient.sendText("hello", "test");
//        Assert.assertEquals(bentenMessage.getAction(),"smalltalk.greetings.hello");
//        Assert.assertEquals(bentenMessage.isActionComplete(),true);
    }

}
