package com.intuit.benten.jira.model;

import java.util.Date;
import java.util.List;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class ChangeLogEntry {

    private User author = null;
    private Date created = null;
    private List<ChangeLogItem> items = null;

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setItems(List<ChangeLogItem> items) {
        this.items = items;
    }


    public User getAuthor() {
        return this.author;
    }

    public Date getCreated() {
        return this.created;
    }

    public List<ChangeLogItem> getItems() {
        return this.items;
    }
}
