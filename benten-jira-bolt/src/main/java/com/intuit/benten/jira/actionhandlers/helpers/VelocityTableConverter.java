package com.intuit.benten.jira.actionhandlers.helpers;

import com.intuit.benten.common.exceptions.TableDimensionsException;
import com.intuit.benten.common.model.Table;
import com.intuit.benten.jira.model.bentenjira.Velocity;
import com.intuit.benten.jira.model.ghc.GhIssue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class VelocityTableConverter {

    public static List<String> storyVelocityToTableRow(GhIssue ghIssue){
        List<String> row = new ArrayList<>();
        row.add(ghIssue.getKey());
        row.add(ghIssue.getSummary());
        row.add(ghIssue.getStatusName());
        row.add(ghIssue.getAssigneeName());
        row.add(ghIssue.getEstimateStatistic().getStatFieldValue().getValue());

        return row;
    }

    public static List<List<String>> sprintVelocityToTableRows(Velocity velocity){
        List<List<String>>  rows
                = velocity.getGhIssues().stream().map(ghIssue -> storyVelocityToTableRow(ghIssue)).collect(Collectors.toList());
        return rows;
    }

    public static List<String> cycleTimeRowHeaders(){
        List<String> rowsHeaders = new ArrayList<String>();
        rowsHeaders.add("Issue-Key");
        rowsHeaders.add("Summary");
        rowsHeaders.add("Status");
        rowsHeaders.add("Assignee");
        rowsHeaders.add("Points");
        return rowsHeaders;
    }

    public static Table sprintVelocitytToTable(Velocity velocity) throws TableDimensionsException {
        Table table = new Table(velocity.getGhIssues().size(),5);
        table.setTitle(velocity.getSprintName());
        table.setRows(sprintVelocityToTableRows(velocity));
        table.setRowHeaders(cycleTimeRowHeaders());
        return table;
    }

    public static List<Table> velocityReportAsTables(List<Velocity> velocities) throws TableDimensionsException{
        List<Table> tables = velocities.stream().map(velocity -> {
            Table table=null;
            try {
                table =sprintVelocitytToTable(velocity);
            } catch (TableDimensionsException e) {
                e.printStackTrace();
            }
            return table;
        }).collect(Collectors.toList());

        return tables;
    }

}
