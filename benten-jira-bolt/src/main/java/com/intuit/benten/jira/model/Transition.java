package com.intuit.benten.jira.model;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class Transition {

    private String id;
    private String name = null;
    private Status to = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getTo() {
        return to;
    }

    public void setTo(Status to) {
        this.to = to;
    }

}
