package com.intuit.benten.jenkins.model;

import java.util.List;

/**
 * Created by sshashidhar on 3/1/18.
 */
public class JenkinsJobBuildParameter {

    String name;
    String value;
    List<String> choices;
    String defaultValue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public String toString() {
        return "JenkinsJobBuildParameters{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", choices=" + choices +
                ", defaultValue='" + defaultValue + '\'' +
                '}';
    }
}
