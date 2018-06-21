package com.intuit.benten.jira.model.bentenjira;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class SprintCycleTime {

    private String sprintName;
    private float averageDevCycleTime;
    private float averageReleaseCycleTime;
    private float averageStoryCycleTime;
    private List<StoryCycleTime> storyCycleTimes;


    public float getAverageDevCycleTime() {
        float averageDevCycleTime =0;
        for (StoryCycleTime storyCycleTime:
             storyCycleTimes) {
            if("Closed".equals(storyCycleTime.getStatus())){
                averageDevCycleTime+=storyCycleTime.getDevCycleTime();
            }

        }

        return averageDevCycleTime;
    }


    public float getAverageReleaseCycleTime() {
        float averageReleaseCycleTime=0;

        for (StoryCycleTime storyCycleTime:
                storyCycleTimes) {
            if (storyCycleTime.isReleased()) {
                averageReleaseCycleTime += storyCycleTime.getReleaseCycleTime();
            }
        }
        return averageReleaseCycleTime;
    }


    public float getAverageStoryCycleTime() {
        float averageStoryCycleTime=0;
        for (StoryCycleTime storyCycleTime:
                storyCycleTimes) {
            if("Closed".equals(storyCycleTime.getStatus())){
                averageDevCycleTime+=storyCycleTime.getDevCycleTime();
                averageStoryCycleTime+=storyCycleTime.getStoryCycleTime();
            }

        }
        return averageStoryCycleTime;
    }


    public List<StoryCycleTime> getStoryCycleTimes() {
        return storyCycleTimes;
    }

    public void setStoryCycleTimes(List<StoryCycleTime> storyCycleTimes) {
        this.storyCycleTimes = storyCycleTimes;
    }

    public void addStoryCycleTimes(StoryCycleTime storyCycleTime) {
        if(this.storyCycleTimes == null)
            this.storyCycleTimes = new ArrayList<>();
        this.storyCycleTimes.add(storyCycleTime);
    }

    public String getSprintName() {
        return sprintName;
    }

    public void setSprintName(String sprintName) {
        this.sprintName = sprintName;
    }
}
