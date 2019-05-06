package com.intuit.benten.jira.actionhandlers.helpers;

import com.intuit.benten.common.exceptions.TableDimensionsException;
import com.intuit.benten.common.model.Table;
import com.intuit.benten.jira.model.bentenjira.SprintCycleTime;
import com.intuit.benten.jira.model.bentenjira.StoryCycleTime;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class CycleTimeTableConverter {

    public static List<String> storyCycleTimeToTableRow(StoryCycleTime storyCycleTime){
        List<String> row = new ArrayList<>();
        row.add(storyCycleTime.getIssueKey());
        row.add(storyCycleTime.getIssueName());
        row.add(storyCycleTime.getStatus());
        if("Closed".equals(storyCycleTime.getStatus())){
            row.add(Float.toString(storyCycleTime.getDevCycleTime()));
        }else{
            row.add("-");
        }
        if(storyCycleTime.isReleased()) {
            row.add(Float.toString(storyCycleTime.getReleaseCycleTime()));
        }else{
            row.add("-");
        }
        if("Closed".equals(storyCycleTime.getStatus()) && storyCycleTime.isReleased() ){
            row.add(Float.toString(storyCycleTime.getStoryCycleTime()));
        }else{
            row.add("-");
        }

        return row;
    }

    public static List<List<String>> sprintCycleTimeToTableRows(SprintCycleTime sprintCycleTime){
        List<List<String>>  rows
                = sprintCycleTime.getStoryCycleTimes().stream().map(storyCycleTime -> storyCycleTimeToTableRow(storyCycleTime)).collect(Collectors.toList());
        return rows;
    }

    public static List<String> cycleTimeRowHeaders(){
        List<String> rowsHeaders = new ArrayList<String>();
        rowsHeaders.add("Issue-Key");
        rowsHeaders.add("Summary");
        rowsHeaders.add("Status");
        rowsHeaders.add("Dev Cycle Time");
        rowsHeaders.add("Release Cycle Time");
        rowsHeaders.add("Story Cycle Time");
        return rowsHeaders;
    }

    public static Table sprintCycleTimeToTable(SprintCycleTime sprintCycleTime) throws TableDimensionsException {
        Table table = new Table(sprintCycleTime.getStoryCycleTimes().size(),6);
        table.setTitle(sprintCycleTime.getSprintName());
        table.setRows(sprintCycleTimeToTableRows(sprintCycleTime));
        table.setRowHeaders(cycleTimeRowHeaders());
        return table;
    }

    public static List<Table> cycleTimeReportAsTables(List<SprintCycleTime> sprintCycleTimes) throws TableDimensionsException{
        List<Table> tables = sprintCycleTimes.stream().map(sprintCycleTime -> {
            Table table=null;
            try {
                table =sprintCycleTimeToTable(sprintCycleTime);
            } catch (TableDimensionsException e) {
                e.printStackTrace();
            }
            return table;
        }).collect(Collectors.toList());

        return tables;
    }

}
