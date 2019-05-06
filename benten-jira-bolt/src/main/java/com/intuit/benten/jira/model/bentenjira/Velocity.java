package com.intuit.benten.jira.model.bentenjira;

import com.intuit.benten.jira.model.ghc.GhIssue;

import java.util.List;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class Velocity {

    private String sprintName;
    private Double totalIssuePoints;
    private Double completedIssuePoints;
    private List<GhIssue> ghIssues;
    private Double averageVelocity;

    public String getSprintName() {
        return sprintName;
    }

    public void setSprintName(String sprintName) {
        this.sprintName = sprintName;
    }

    public Double getTotalIssuePoints() {
        return totalIssuePoints;
    }

    public void setTotalIssuePoints(Double totalIssuePoints) {
        this.totalIssuePoints = totalIssuePoints;
    }

    public Double getCompletedIssuePoints() {
        return completedIssuePoints;
    }

    public void setCompletedIssuePoints(Double completedIssuePoints) {
        this.completedIssuePoints = completedIssuePoints;
    }

    public Double getAverageVelocity() {
        return averageVelocity;
    }

    public void setAverageVelocity(Double averageVelocity) {
        this.averageVelocity = averageVelocity;
    }


    public List<GhIssue> getGhIssues() {
        return ghIssues;
    }

    public void setGhIssues(List<GhIssue> ghIssues) {
        this.ghIssues = ghIssues;
    }
}
