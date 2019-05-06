package com.intuit.benten.jira.actionhandlers;

import com.intuit.benten.common.annotations.ActionHandler;
import com.intuit.benten.jira.BentenJiraClient;
import com.intuit.benten.common.actionhandlers.BentenActionHandler;
import com.intuit.benten.common.actionhandlers.BentenHandlerResponse;
import com.intuit.benten.common.actionhandlers.BentenHtmlResponse;
import com.intuit.benten.jira.actionhandlers.helpers.VelocityTableConverter;
import com.intuit.benten.jira.exceptions.BentenJiraException;
import com.intuit.benten.common.helpers.BentenMessageHelper;
import com.intuit.benten.common.model.Table;
import com.intuit.benten.common.model.TableToHtmlConverter;
import com.intuit.benten.jira.model.agile.Board;
import com.intuit.benten.jira.model.bentenjira.Velocity;
import com.intuit.benten.jira.model.ghc.Sprint;
import com.intuit.benten.jira.model.ghc.SprintReport;
import com.intuit.benten.common.nlp.BentenMessage;
import com.intuit.benten.jira.utils.SlackMessageRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
@Component
@ActionHandler(action = JiraActions.ACTION_JIRA_SPRINT_VELOCITY)
public class JiraSprintVelocityActionHandler implements BentenActionHandler {

    @Autowired
    private BentenJiraClient bentenJiraClient;

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

            List<Velocity> sprintVelocities =
                    closedSprints.stream().map((sprint) -> {
                        return velocity(sprint, boards.get(0));
                    }).collect(Collectors.toList());
            List<Table> velocityTables = null;

            velocityTables = VelocityTableConverter.velocityReportAsTables(sprintVelocities);


            List<String> htmlTables =
                    velocityTables.stream().map((velocityTable) -> {
                        return TableToHtmlConverter.convertTableToHtmlTable(velocityTable);
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
        }catch (BentenJiraException e) {
            bentenHandlerResponse
                    .setBentenSlackResponse(SlackMessageRenderer.errorMessage(e.getMessage().substring(e.getMessage().lastIndexOf(":") + 1)));
        } catch (Exception e) {
            throw new BentenJiraException(e.getMessage());
        }

        return bentenHandlerResponse;
    }

    public Velocity velocity(Sprint sprint, Board board) {
        SprintReport sprintReport = bentenJiraClient.getSprintReport(board.getId(),sprint.getId());

        Velocity velocity = new Velocity();
        velocity.setCompletedIssuePoints(sprintReport.getContents().getCompletedIssuesEstimateSum().getValue());
        velocity.setGhIssues(sprintReport.getContents().getCompletedIssues());
        velocity.setSprintName(sprintReport.getSprint().getName());
        velocity.setTotalIssuePoints(sprintReport.getContents().getAllIssuesEstimateSum().getValue());

        return velocity;
    }

}
