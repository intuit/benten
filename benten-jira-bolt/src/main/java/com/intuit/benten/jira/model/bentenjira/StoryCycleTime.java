package com.intuit.benten.jira.model.bentenjira;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class StoryCycleTime {

    private String issueKey;
    private String issueName;
    private String status;
    private float devCycleTime;
    private float releaseCycleTime;
    private float storyCycleTime;
    private Date completedOn;
    private Date createdOn;
    private Date releaseDate;
    private boolean isReleased=false;

    public String getIssueKey() {
        return issueKey;
    }

    public void setIssueKey(String issueKey) {
        this.issueKey = issueKey;
    }

    public String getIssueName() {
        return issueName;
    }

    public void setIssueName(String issueName) {
        this.issueName = issueName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getDevCycleTime() {

        float devCycleTime=0;
        if("Closed".equals(this.status)){

            if(createdOn == null  || completedOn==null){
                this.setStatus("Issue directly moved to Closed state");
                return 0;
            }

            devCycleTime =differenceInDaysExcludeWeekends(createdOn,completedOn);
        }

        return devCycleTime;
    }

    public float getReleaseCycleTime() {
      float releaseCycleTime=0;
        if(isReleased){
            if(completedOn == null || releaseDate == null){
                this.setReleased(false);
                return 0;
            }
            releaseCycleTime
                    =differenceInDaysExcludeWeekends(completedOn,releaseDate);
        }
        return releaseCycleTime;
    }

    public float getStoryCycleTime() {
       float storyCycleTime=0;
        if(this.status.equals("Closed"))
            storyCycleTime+=this.getDevCycleTime();
        if(isReleased)
            storyCycleTime+=this.getReleaseCycleTime();
        return storyCycleTime;
    }

    public Date getCompletedOn() {
        return completedOn;
    }

    public void setCompletedOn(Date completedOn) {
        this.completedOn = completedOn;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public boolean isReleased() {
        return isReleased;
    }

    public void setReleased(boolean released) {
        isReleased = released;
    }

    public float differenceInDaysExcludeWeekends(Date startDate,Date endDate){
        Calendar calStart = Calendar.getInstance();
        Calendar calEnd = Calendar.getInstance();
        calStart.setTime(startDate);
        calEnd.setTime(endDate);

        float numberOfDays = 0;
        while (calStart.before(calEnd)) {
            if ((Calendar.SATURDAY != calStart.get(Calendar.DAY_OF_WEEK))
                    &&(Calendar.SUNDAY != calStart.get(Calendar.DAY_OF_WEEK))) {
                numberOfDays++;
            }
            calStart.add(Calendar.DATE,1);
        }

        return numberOfDays;
    }
}
