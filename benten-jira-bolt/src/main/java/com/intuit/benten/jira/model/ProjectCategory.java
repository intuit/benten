package com.intuit.benten.jira.model;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class ProjectCategory {
    private String name = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description = null;
}
