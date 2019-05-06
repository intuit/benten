package com.intuit.benten.jira.model;


import java.util.List;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class ChangeLog {

    private List<ChangeLogEntry> histories = null;

    public List<ChangeLogEntry> getHistories() {
        return histories;
    }

    public void setHistories(List<ChangeLogEntry> histories) {
        this.histories = histories;
    }
}
