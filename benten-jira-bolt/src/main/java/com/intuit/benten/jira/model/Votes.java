package com.intuit.benten.jira.model;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class Votes {
    private int votes = 0;
    private boolean hasVoted = false;

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public boolean isHasVoted() {
        return hasVoted;
    }

    public void setHasVoted(boolean hasVoted) {
        this.hasVoted = hasVoted;
    }
}
