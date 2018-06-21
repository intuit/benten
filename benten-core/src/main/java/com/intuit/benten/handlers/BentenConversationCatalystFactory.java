package com.intuit.benten.handlers;

import com.intuit.benten.common.annotations.BentenConversationCatalyst;
import com.intuit.benten.common.conversationcatalysts.ConversationCatalyst;
import com.intuit.benten.common.conversationcatalysts.ConversationCatalystFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
@Component
public class BentenConversationCatalystFactory implements ConversationCatalystFactory {

    private static final Logger logger = LoggerFactory.getLogger(BentenConversationCatalystFactory.class);

    @Autowired
    private ApplicationContext applicationContext;

    HashMap<String,ConversationCatalyst> conversationCatalystMap = new HashMap<>();

    ClassPathScanningCandidateComponentProvider scanner;

    @PostConstruct
    public void init() throws ClassNotFoundException {
        scanner =
                new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(BentenConversationCatalyst.class));

        for (BeanDefinition bd : scanner.findCandidateComponents("com.intuit.benten"))
        {
            Object object =  applicationContext.getBean(Class.forName(bd.getBeanClassName()));
            conversationCatalystMap.put(object.getClass().getAnnotation(BentenConversationCatalyst.class).action(),(ConversationCatalyst) object);
        }

    }


    @Override
    public HashMap<String, ConversationCatalyst> getConversationCatalystMap() {
        return conversationCatalystMap;
    }
}
