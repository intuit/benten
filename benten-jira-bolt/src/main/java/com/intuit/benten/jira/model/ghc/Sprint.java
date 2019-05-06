package com.intuit.benten.jira.model.ghc;

import java.util.Date;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class Sprint extends GhResource {
    private String state;
    private long originBoardId;
    private Date startDate;
    private Date endDate;
    private Date completeDate;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getOriginBoardId() {
        return originBoardId;
    }

    public void setOriginBoardId(long originBoardId) {
        this.originBoardId = originBoardId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }
}
