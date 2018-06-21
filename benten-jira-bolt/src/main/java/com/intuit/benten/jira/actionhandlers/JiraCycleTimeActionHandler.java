package com.intuit.benten.jira.actionhandlers;

import com.intuit.benten.common.actionhandlers.BentenActionHandler;
import com.intuit.benten.common.actionhandlers.BentenHandlerResponse;
import com.intuit.benten.common.actionhandlers.BentenHtmlResponse;
import com.intuit.benten.common.annotations.ActionHandler;
import com.intuit.benten.jira.actionhandlers.helpers.CycleTimeTableConverter;
import com.intuit.benten.jira.BentenJiraClient;
import com.intuit.benten.jira.actionhandlers.helpers.CycleTimeCalculator;
import com.intuit.benten.jira.exceptions.BentenJiraException;
import com.intuit.benten.common.helpers.BentenMessageHelper;
import com.intuit.benten.jira.model.Issue;
import com.intuit.benten.common.model.Table;
import com.intuit.benten.common.model.TableToHtmlConverter;
import com.intuit.benten.jira.model.ghc.Sprint;
import com.intuit.benten.jira.model.agile.Board;
import com.intuit.benten.jira.model.bentenjira.SprintCycleTime;
import com.intuit.benten.jira.model.bentenjira.StoryCycleTime;
import com.intuit.benten.jira.model.ghc.SprintReport;
import com.intuit.benten.common.nlp.BentenMessage;
import com.intuit.benten.jira.utils.SlackMessageRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
@Component
@ActionHandler(action = JiraActions.ACTION_JIRA_CYCLE_TIME)
public class JiraCycleTimeActionHandler implements BentenActionHandler {

    @Autowired
    private BentenJiraClient bentenJiraClient;

    @Autowired
    private CycleTimeCalculator cycleTimeCalculator;

    private SimpleDateFormat df;

    public BentenHandlerResponse handle(BentenMessage bentenMessage) {

        String boardName = BentenMessageHelper.getParameterAsString(bentenMessage, JiraActionParameters.PARAMETER_BOARD_NAME);
        String noOfSprints = BentenMessageHelper.getParameterAsString(bentenMessage, JiraActionParameters.PARAMETER_NO_OF_SPRINTS);

        BentenHandlerResponse bentenHandlerResponse = new BentenHandlerResponse();

        try {
            List<Board> boards = bentenJiraClient.getBoardsByName(boardName);

            List<Sprint> sprints = bentenJiraClient.getSprintsByBoardId(boards.get(0).getId());
            Collections.reverse(sprints);

            List<Sprint> closedSprints = sprints.stream()
                    .filter(sprint -> sprint.getState().equals("CLOSED"))
                    .collect(Collectors.toList());
            closedSprints = closedSprints.subList(0, Integer.parseInt(noOfSprints));

            List<SprintCycleTime> sprintCycleTimes =
                    closedSprints.stream().map((sprint) -> {
                        return cycleTime(sprint, boards.get(0));
                    }).collect(Collectors.toList());
            List<Table> cycleTimeTables = null;

            cycleTimeTables = CycleTimeTableConverter.cycleTimeReportAsTables(sprintCycleTimes);


            List<String> htmlTables =
                    cycleTimeTables.stream().map((cycleTimeTable) -> {
                        return TableToHtmlConverter.convertTableToHtmlTable(cycleTimeTable);
                    }).collect(Collectors.toList());

            StringBuilder htmlResponse = new StringBuilder("");

            for (String htmlTable :
                    htmlTables) {
                htmlResponse.append(htmlTable);
            }

            BentenHtmlResponse bentenHtmlResponse =
                    new BentenHtmlResponse();
            bentenHtmlResponse.setHtml(htmlResponse.toString());
            bentenHandlerResponse
                    .setBentenHtmlResponse(bentenHtmlResponse);
        } catch (BentenJiraException e) {
            bentenHandlerResponse
                    .setBentenSlackResponse(SlackMessageRenderer.errorMessage(e.getMessage().substring(e.getMessage().lastIndexOf(":") + 1)));
        } catch (Exception e) {
            throw new BentenJiraException(e.getMessage());
        }

        return bentenHandlerResponse;
    }

    public SprintCycleTime cycleTime(Sprint sprint, Board board) {
        SprintReport sprintReport = bentenJiraClient.getSprintReport(board.getId(), sprint.getId());
        List<Issue> issues = bentenJiraClient.getSprintIssues(sprint.getId());
        SprintCycleTime sprintCycleTime = new SprintCycleTime();
        sprintCycleTime.setSprintName(sprint.getName());
        for (Issue issue
                : issues) {
            StoryCycleTime storyCycleTime = null;
            storyCycleTime = cycleTimeCalculator.storyCycleTime(issue, sprintReport);
            sprintCycleTime.addStoryCycleTimes(storyCycleTime);
        }
        return sprintCycleTime;
    }

}
