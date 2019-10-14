package com.intuit.benten.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
@Component
@ConfigurationProperties("benten.ai")
@PropertySource(value = "classpath:benten.properties")
public class AiProperties {

    private String projectId;

    public String getProjectId() { return projectId; }

    public void setProjectId(String projectId) { this.projectId = projectId; }

}
