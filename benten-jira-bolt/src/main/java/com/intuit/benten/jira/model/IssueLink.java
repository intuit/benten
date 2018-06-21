package com.intuit.benten.jira.model;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class IssueLink {
    private LinkType type = null;
    private Issue inwardIssue = null;
    private Issue outwardIssue = null;

    public LinkType getType() {
        return type;
    }

    public void setType(LinkType type) {
        this.type = type;
    }

    public Issue getInwardIssue() {
        return inwardIssue;
    }

    public void setInwardIssue(Issue inwardIssue) {
        this.inwardIssue = inwardIssue;
    }

    public Issue getOutwardIssue() {
        return outwardIssue;
    }

    public void setOutwardIssue(Issue outwardIssue) {
        this.outwardIssue = outwardIssue;
    }
}
