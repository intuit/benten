package com.intuit.benten.jira.model.ghc;

import java.util.List;
import java.util.Map;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class Contents {

    private List<GhIssue> completedIssues = null;
    private List<GhIssue> issuesNotCompletedInCurrentSprint = null;
    private List<GhIssue> issuesCompletedInCurrentSprint = null;
    private List<GhIssue> issuesCompletedInAnotherSprint = null;
    private List<GhIssue> puntedIssues = null;
    private EstimateSum completedIssuesEstimateSum = null;
    private EstimateSum completedIssuesInitialEstimateSum = null;
    private EstimateSum issuesNotCompletedInitialEstimateSum = null;
    private EstimateSum issuesNotCompletedEstimateSum = null;
    private EstimateSum allIssuesEstimateSum = null;
    private EstimateSum puntedIssuesEstimateSum = null;
    private EstimateSum puntedIssuesInitialEstimateSum = null;
    private EstimateSum issuesCompletedInAnotherSprintInitialEstimateSum = null;
    private EstimateSum issuesCompletedInAnotherSprintEstimateSum = null;
    private Map<String,Boolean> issueKeysAddedDuringSprint = null;

    public List<GhIssue> getCompletedIssues() {
        return completedIssues;
    }

    public void setCompletedIssues(List<GhIssue> completedIssues) {
        this.completedIssues = completedIssues;
    }

    public List<GhIssue> getIssuesNotCompletedInCurrentSprint() {
        return issuesNotCompletedInCurrentSprint;
    }

    public void setIssuesNotCompletedInCurrentSprint(List<GhIssue> issuesNotCompletedInCurrentSprint) {
        this.issuesNotCompletedInCurrentSprint = issuesNotCompletedInCurrentSprint;
    }

    public List<GhIssue> getIssuesCompletedInCurrentSprint() {
        return issuesCompletedInCurrentSprint;
    }

    public void setIssuesCompletedInCurrentSprint(List<GhIssue> issuesCompletedInCurrentSprint) {
        this.issuesCompletedInCurrentSprint = issuesCompletedInCurrentSprint;
    }

    public List<GhIssue> getIssuesCompletedInAnotherSprint() {
        return issuesCompletedInAnotherSprint;
    }

    public void setIssuesCompletedInAnotherSprint(List<GhIssue> issuesCompletedInAnotherSprint) {
        this.issuesCompletedInAnotherSprint = issuesCompletedInAnotherSprint;
    }

    public List<GhIssue> getPuntedIssues() {
        return puntedIssues;
    }

    public void setPuntedIssues(List<GhIssue> puntedIssues) {
        this.puntedIssues = puntedIssues;
    }

    public EstimateSum getCompletedIssuesEstimateSum() {
        return completedIssuesEstimateSum;
    }

    public void setCompletedIssuesEstimateSum(EstimateSum completedIssuesEstimateSum) {
        this.completedIssuesEstimateSum = completedIssuesEstimateSum;
    }

    public EstimateSum getCompletedIssuesInitialEstimateSum() {
        return completedIssuesInitialEstimateSum;
    }

    public void setCompletedIssuesInitialEstimateSum(EstimateSum completedIssuesInitialEstimateSum) {
        this.completedIssuesInitialEstimateSum = completedIssuesInitialEstimateSum;
    }

    public EstimateSum getIssuesNotCompletedInitialEstimateSum() {
        return issuesNotCompletedInitialEstimateSum;
    }

    public void setIssuesNotCompletedInitialEstimateSum(EstimateSum issuesNotCompletedInitialEstimateSum) {
        this.issuesNotCompletedInitialEstimateSum = issuesNotCompletedInitialEstimateSum;
    }

    public EstimateSum getIssuesNotCompletedEstimateSum() {
        return issuesNotCompletedEstimateSum;
    }

    public void setIssuesNotCompletedEstimateSum(EstimateSum issuesNotCompletedEstimateSum) {
        this.issuesNotCompletedEstimateSum = issuesNotCompletedEstimateSum;
    }

    public EstimateSum getAllIssuesEstimateSum() {
        return allIssuesEstimateSum;
    }

    public void setAllIssuesEstimateSum(EstimateSum allIssuesEstimateSum) {
        this.allIssuesEstimateSum = allIssuesEstimateSum;
    }

    public EstimateSum getPuntedIssuesEstimateSum() {
        return puntedIssuesEstimateSum;
    }

    public void setPuntedIssuesEstimateSum(EstimateSum puntedIssuesEstimateSum) {
        this.puntedIssuesEstimateSum = puntedIssuesEstimateSum;
    }

    public EstimateSum getPuntedIssuesInitialEstimateSum() {
        return puntedIssuesInitialEstimateSum;
    }

    public void setPuntedIssuesInitialEstimateSum(EstimateSum puntedIssuesInitialEstimateSum) {
        this.puntedIssuesInitialEstimateSum = puntedIssuesInitialEstimateSum;
    }

    public EstimateSum getIssuesCompletedInAnotherSprintInitialEstimateSum() {
        return issuesCompletedInAnotherSprintInitialEstimateSum;
    }

    public void setIssuesCompletedInAnotherSprintInitialEstimateSum(EstimateSum issuesCompletedInAnotherSprintInitialEstimateSum) {
        this.issuesCompletedInAnotherSprintInitialEstimateSum = issuesCompletedInAnotherSprintInitialEstimateSum;
    }

    public EstimateSum getIssuesCompletedInAnotherSprintEstimateSum() {
        return issuesCompletedInAnotherSprintEstimateSum;
    }

    public void setIssuesCompletedInAnotherSprintEstimateSum(EstimateSum issuesCompletedInAnotherSprintEstimateSum) {
        this.issuesCompletedInAnotherSprintEstimateSum = issuesCompletedInAnotherSprintEstimateSum;
    }

    public Map<String, Boolean> getIssueKeysAddedDuringSprint() {
        return issueKeysAddedDuringSprint;
    }

    public void setIssueKeysAddedDuringSprint(Map<String, Boolean> issueKeysAddedDuringSprint) {
        this.issueKeysAddedDuringSprint = issueKeysAddedDuringSprint;
    }
}
