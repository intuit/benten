package com.intuit.benten.jira.model;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class Status {

    private String id;
    private String description = null;
    private String iconUrl = null;
    private String name = null;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
