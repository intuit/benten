package com.intuit.benten.jira.model.ghc;

import java.util.List;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class GhIssue {

    private String key = null;
    private boolean hidden = false;
    private String summary = null;
    private String typeName = null;
    private String typeId = null;
    private String typeUrl = null;
    private String priorityUrl = null;
    private String priorityName = null;
    private boolean done = false;
    private String assignee = null;
    private String assigneeName = null;
    private String avatarUrl = null;
    private String colour = null;
    private String statusId = null;
    private String statusName = null;
    private String statusUrl = null;
    private List<Integer> fixVersions = null;
    private int projectId = 0;
    private EstimateStatistic estimateStatistic;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeUrl() {
        return typeUrl;
    }

    public void setTypeUrl(String typeUrl) {
        this.typeUrl = typeUrl;
    }

    public String getPriorityUrl() {
        return priorityUrl;
    }

    public void setPriorityUrl(String priorityUrl) {
        this.priorityUrl = priorityUrl;
    }

    public String getPriorityName() {
        return priorityName;
    }

    public void setPriorityName(String priorityName) {
        this.priorityName = priorityName;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getAssigneeName() {
        return assigneeName;
    }

    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusUrl() {
        return statusUrl;
    }

    public void setStatusUrl(String statusUrl) {
        this.statusUrl = statusUrl;
    }

    public List<Integer> getFixVersions() {
        return fixVersions;
    }

    public void setFixVersions(List<Integer> fixVersions) {
        this.fixVersions = fixVersions;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public EstimateStatistic getEstimateStatistic() {
        return estimateStatistic;
    }

    public void setEstimateStatistic(EstimateStatistic estimateStatistic) {
        this.estimateStatistic = estimateStatistic;
    }
}
