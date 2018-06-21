package com.intuit.benten.jira.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class Watches {
    private String name = null;
    private int watchCount = 0;
    private boolean isWatching = false;
    private List<User> watchers = new ArrayList();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWatchCount() {
        return watchCount;
    }

    public void setWatchCount(int watchCount) {
        this.watchCount = watchCount;
    }

    public boolean isWatching() {
        return isWatching;
    }

    public void setWatching(boolean watching) {
        isWatching = watching;
    }

    public List<User> getWatchers() {
        return watchers;
    }

    public void setWatchers(List<User> watchers) {
        this.watchers = watchers;
    }

}
