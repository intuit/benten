package com.intuit.benten.handlers;

import com.intuit.benten.common.actionhandlers.ActionHandlerFactory;
import com.intuit.benten.common.actionhandlers.BentenActionHandler;
import com.intuit.benten.common.annotations.ActionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.HashMap;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
@Component
public class BentenActionHandlerFactory implements ActionHandlerFactory {

    private static final Logger logger = LoggerFactory.getLogger(BentenActionHandlerFactory.class);

    @Autowired
    private ApplicationContext applicationContext;

    HashMap<String, BentenActionHandler> actionHandlerMap;

    ClassPathScanningCandidateComponentProvider scanner;

    public BentenActionHandlerFactory() throws NoSuchFieldException {
        actionHandlerMap = new HashMap<>();
    }

    @PostConstruct
    public void init() throws ClassNotFoundException {

        scanner =
                new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(ActionHandler.class));

        for (BeanDefinition bd : scanner.findCandidateComponents(""))
        {
            Object object =  applicationContext.getBean(Class.forName(bd.getBeanClassName()));
            actionHandlerMap.put(object.getClass().getAnnotation(ActionHandler.class).action(),(BentenActionHandler) object);
        }
    }

    public BentenActionHandler getActionHandler(String action) {
        BentenActionHandler actionHandler = this.actionHandlerMap.get(action);
        return actionHandler;
    }

    @Override
    public HashMap<String, BentenActionHandler> getActionHandlerMap() {
        return actionHandlerMap;
    }
}
