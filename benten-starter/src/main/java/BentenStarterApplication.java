
import com.intuit.benten.mock.MockFeatureServerRunner;
import com.intuit.benten.nlp.NlpClient;
import com.intuit.benten.nlp.dialogflow.DialogFlowClient;
import com.intuit.benten.properties.AiProperties;
import com.intuit.benten.properties.BentenProxyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;


@SpringBootApplication(scanBasePackages={"com.intuit.benten","com.example"})
@EnableAutoConfiguration
public class BentenStarterApplication extends SpringBootServletInitializer {

    @Autowired
    AiProperties aiProperties;

    @Autowired
    BentenProxyConfig bentenProxyConfig;

    @Autowired
    MockFeatureServerRunner mockFeatureServerRunner;

    @Bean
    public NlpClient aiClient(){

        return new DialogFlowClient(aiProperties.getToken(),bentenProxyConfig);
    }

    @PostConstruct
    public void setup(){
        try {
            mockFeatureServerRunner.testServer();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {

        ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(BentenStarterApplication.class)
                .properties("spring.config.name:benten")
                .build().run();

    }

}