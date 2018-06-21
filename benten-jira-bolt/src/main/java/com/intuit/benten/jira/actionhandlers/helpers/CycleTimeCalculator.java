package com.intuit.benten.jira.actionhandlers.helpers;

import com.intuit.benten.jira.model.ChangeLog;
import com.intuit.benten.jira.model.ChangeLogItem;
import com.intuit.benten.jira.model.ChangeLogEntry;
import com.intuit.benten.jira.model.Issue;
import com.intuit.benten.jira.model.bentenjira.StoryCycleTime;
import com.intuit.benten.jira.model.ghc.SprintReport;
import org.springframework.stereotype.Component;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
@Component
public class CycleTimeCalculator {
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    public  StoryCycleTime storyCycleTime(Issue issue, SprintReport sprint) {

        ChangeLog changeLog = issue.getChangeLog();
        if (!(changeLog != null && changeLog.getHistories() != null && !changeLog.getHistories().isEmpty())) {
            return null;
        }

        StoryCycleTime storyCycleTime = new StoryCycleTime();
        storyCycleTime.setIssueKey(issue.getKey());
        storyCycleTime.setIssueName(issue.getSummary());
        storyCycleTime.setStatus(issue.getStatus().getName());
        for (ChangeLogEntry changeLogEntry
                : changeLog.getHistories()) {

            for (ChangeLogItem changeLogItem
                    : changeLogEntry.getItems()) {
                if (changeLogItem.getField().equals("Released")) {

                    if(storyCycleTime.getCompletedOn()!=null) {
                        storyCycleTime.setReleased(true);
                        Date result = df.parse(changeLogItem.getTo(), new ParsePosition(0));
                        storyCycleTime.setReleaseDate(result);
                    }
                }
                if (changeLogItem.getField().equals("status")) {
                    if ((changeLogItem.getFromString().equals("New") || changeLogItem.getFromString().equals("Open"))
                            && changeLogItem.getToString().equals("In Progress")) {
                        storyCycleTime.setCreatedOn(changeLogEntry.getCreated());
                    }
                    if ((changeLogItem.getFromString().equals("In Progress") && changeLogItem.getToString().equals("Closed"))) {
                        storyCycleTime.setCompletedOn(changeLogEntry.getCreated());
                    }
                    if ((changeLogItem.getFromString().equals("Verify") && changeLogItem.getToString().equals("Closed"))) {
                        storyCycleTime.setCompletedOn(changeLogEntry.getCreated());
                    }

                        if ((storyCycleTime.getCreatedOn() != null && storyCycleTime.getCompletedOn() != null)
                                && (storyCycleTime.getCompletedOn().compareTo(sprint.getSprint().getCompleteDate()))>0) {
                            storyCycleTime.setStatus("Closed In Later Sprints");
                        }

                }
            }

        }

        if("Closed".equals(storyCycleTime.getStatus())){
            if(storyCycleTime.getCreatedOn() ==null || storyCycleTime.getCompletedOn()==null){
                storyCycleTime.setStatus("Issue directly moved to Completed");
            }
        }
        return storyCycleTime;
    }
}
