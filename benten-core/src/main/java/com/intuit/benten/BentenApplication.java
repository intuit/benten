package com.intuit.benten;

import com.intuit.benten.hackernews.properties.BentenProxyConfig;
import com.intuit.benten.hackernews.properties.AiProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;


@ComponentScan("com.intuit.benten")
public class BentenApplication  {

    @Autowired
    AiProperties aiProperties;

    @Autowired
    BentenProxyConfig bentenProxyConfig;

    @PostConstruct
    public void startup(){
        try {
          //  MockFeatureServerRunner.testServer();
        }catch(Exception ex){
            //do nothing
        }
    }
}