package com.intuit.benten.jira.model;


import net.sf.json.JSONObject;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class IssueType {
    private String description = null;
    private String iconUrl = null;
    private String name = null;
    private boolean subtask = false;
    private JSONObject fields = null;

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

    public boolean isSubtask() {
        return subtask;
    }

    public void setSubtask(boolean subtask) {
        this.subtask = subtask;
    }

    public JSONObject getFields() {
        return fields;
    }

    public void setFields(JSONObject fields) {
        this.fields = fields;
    }
}
