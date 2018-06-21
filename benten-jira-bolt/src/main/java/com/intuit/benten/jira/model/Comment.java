package com.intuit.benten.jira.model;

import java.util.Date;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class Comment {
    private String issueKey = null;
    private User author = null;
    private String body = null;
    private Date created = null;
    private Date updated = null;
    private User updatedAuthor = null;
    private Visibility visibility = null;

    public String getIssueKey() {
        return issueKey;
    }

    public void setIssueKey(String issueKey) {
        this.issueKey = issueKey;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public User getUpdatedAuthor() {
        return updatedAuthor;
    }

    public void setUpdatedAuthor(User updatedAuthor) {
        this.updatedAuthor = updatedAuthor;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }
}
