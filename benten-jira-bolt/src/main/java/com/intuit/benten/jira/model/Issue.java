package com.intuit.benten.jira.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.benten.jira.converters.JiraConverter;
import com.intuit.benten.jira.http.JiraHttpHelper;
import net.sf.json.JSONObject;

import java.io.IOException;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class Issue {
    private String key = null;
    private Map fields = null;
    private User assignee = null;
    private List<Attachment> attachments = null;
    private ChangeLog changeLog = null;
    private List<Comment> comments = null;
    private List<Component> components = null;
    private String description = null;
    private Date dueDate = null;
    private List<Version> fixVersions = null;
    private List<IssueLink> issueLinks = null;
    private IssueType issueType = null;
    private List<String> labels = null;
    private Issue parent = null;
    private Priority priority = null;
    private Project project = null;
    private User reporter = null;
    private Resolution resolution = null;
    private Date resolutionDate = null;
    private Status status = null;
    private List<Issue> subtasks = null;
    private String summary = null;
    private TimeTracking timeTracking = null;
    private List<Version> versions = null;
    private Votes votes = null;
    private Watches watches = null;
    private List<WorkLog> workLogs = null;
    private Integer timeEstimate = null;
    private Integer timeSpent = null;
    private Date createdDate = null;
    private Date updatedDate = null;
    private Security security = null;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Map getFields() {
        return fields;
    }

    public void setFields(Map fields) {
        this.fields = fields;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public ChangeLog getChangeLog() {
        return changeLog;
    }

    public void setChangeLog(ChangeLog changeLog) {
        this.changeLog = changeLog;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public List<Version> getFixVersions() {
        return fixVersions;
    }

    public void setFixVersions(List<Version> fixVersions) {
        this.fixVersions = fixVersions;
    }

    public List<IssueLink> getIssueLinks() {
        return issueLinks;
    }

    public void setIssueLinks(List<IssueLink> issueLinks) {
        this.issueLinks = issueLinks;
    }

    public IssueType getIssueType() {
        return issueType;
    }

    public void setIssueType(IssueType issueType) {
        this.issueType = issueType;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public Issue getParent() {
        return parent;
    }

    public void setParent(Issue parent) {
        this.parent = parent;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getReporter() {
        return reporter;
    }

    public void setReporter(User reporter) {
        this.reporter = reporter;
    }

    public Resolution getResolution() {
        return resolution;
    }

    public void setResolution(Resolution resolution) {
        this.resolution = resolution;
    }

    public Date getResolutionDate() {
        return resolutionDate;
    }

    public void setResolutionDate(Date resolutionDate) {
        this.resolutionDate = resolutionDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Issue> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<Issue> subtasks) {
        this.subtasks = subtasks;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public TimeTracking getTimeTracking() {
        return timeTracking;
    }

    public void setTimeTracking(TimeTracking timeTracking) {
        this.timeTracking = timeTracking;
    }

    public List<Version> getVersions() {
        return versions;
    }

    public void setVersions(List<Version> versions) {
        this.versions = versions;
    }

    public Votes getVotes() {
        return votes;
    }

    public void setVotes(Votes votes) {
        this.votes = votes;
    }

    public Watches getWatches() {
        return watches;
    }

    public void setWatches(Watches watches) {
        this.watches = watches;
    }

    public List<WorkLog> getWorkLogs() {
        return workLogs;
    }

    public void setWorkLogs(List<WorkLog> workLogs) {
        this.workLogs = workLogs;
    }

    public Integer getTimeEstimate() {
        return timeEstimate;
    }

    public void setTimeEstimate(Integer timeEstimate) {
        this.timeEstimate = timeEstimate;
    }

    public Integer getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(Integer timeSpent) {
        this.timeSpent = timeSpent;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Security getSecurity() {
        return security;
    }

    public void setSecurity(Security security) {
        this.security = security;
    }

    public String getUrl(){
        String url= null;
        try{
            url= JiraHttpHelper.issueUri(this.key).toString();
        }catch(Exception ex){
            ex.getStackTrace();
        }
        return url;
    }

    public Issue(JSONObject jsonObject) throws IOException {
        ObjectMapper objectMapper = JiraConverter.objectMapper;
        JSONObject comment =null;
        this.key = jsonObject.getString("key");
        if(jsonObject.containsKey("changelog") && !isNull(jsonObject.get("changelog"))){
            this.changeLog= objectMapper.readValue(jsonObject.get("changelog").toString(), ChangeLog.class);
        }
        this.fields = (Map)jsonObject.get("fields");
        if(this.fields.containsKey("assignee") && !isNull(this.fields.get("assignee")))
            this.assignee = objectMapper.readValue(this.fields.get("assignee").toString(),User.class);
        if(this.fields.containsKey("attachments") && !isNull(this.fields.get("attachments")))
            this.attachments = objectMapper.readValue(this.fields.get("attachments").toString(),new TypeReference<List<Attachment>>(){});
        if(this.fields.containsKey("comment") && !isNull(this.fields.get("comment"))){
             comment= objectMapper.readValue(this.fields.get("comment").toString(), JSONObject.class);
             if(comment!=null){
                 if(comment.containsKey("comments"))
                     this.comments = objectMapper.readValue(comment.get("comments").toString(),new TypeReference<List<Comment>>(){});
             }
        }
        if(this.fields.containsKey("components") && !isNull(this.fields.get("components")) )
            this.components = objectMapper.readValue(this.fields.get("components").toString(),new TypeReference<List<Component>>(){});
        if(this.fields.containsKey("description") && !isNull(this.fields.get("description")))
            this.description = this.fields.get("description").toString();
        if(this.fields.containsKey("duedate")  && !isNull(this.fields.get("duedate"))) {
            this.dueDate = JiraConverter.getDateTime((String) this.fields.get("duedate"));
        }
        if(this.fields.containsKey("fixVersions") && !isNull(this.fields.get("fixVersions")))
            this.fixVersions = objectMapper.readValue(this.fields.get("fixVersions").toString(),new TypeReference<List<Version>>(){});

        if(this.fields.containsKey("issuetype") && !isNull(this.fields.get("issuetype")))
            this.issueType = objectMapper.readValue(this.fields.get("issuetype").toString(),IssueType.class);
        if(this.fields.containsKey("labels") && !isNull(this.fields.get("labels")))
            this.labels = objectMapper.readValue(this.fields.get("labels").toString(),new TypeReference<List<String>>(){});
        if(this.fields.containsKey("priority") && !isNull(this.fields.get("priority")))
            this.priority = objectMapper.readValue(this.fields.get("priority").toString(),Priority.class);
        if(this.fields.containsKey("project") && !isNull(this.fields.get("project")))
            this.project = objectMapper.readValue(this.fields.get("project").toString(),Project.class);
        if(this.fields.containsKey("reporter") && !isNull(this.fields.get("reporter")))
            this.reporter = objectMapper.readValue(this.fields.get("reporter").toString(),User.class);
        if(this.fields.containsKey("resolution") && !isNull(this.fields.get("resolution")))
            this.resolution = objectMapper.readValue(this.fields.get("resolution").toString(),Resolution.class);
        if(this.fields.containsKey("resolutiondate") && !isNull(this.fields.get("resolutiondate"))) {
            this.resolutionDate = JiraConverter.getDateTime((String)this.fields.get("resolutiondate"));
        }
        if(this.fields.containsKey("status") && !isNull(this.fields.get("status")))
            this.status = objectMapper.readValue(this.fields.get("status").toString(),Status.class);
        if(this.fields.containsKey("summary") && !isNull(this.fields.get("summary")))
            this.summary = this.fields.get("summary").toString();
        if(this.fields.containsKey("timetracking") && !isNull(this.fields.get("timetracking")))
            this.timeTracking = objectMapper.readValue(this.fields.get("timetracking").toString(),TimeTracking.class);
        if(this.fields.containsKey("versions") && !isNull(this.fields.get("versions")))
            this.versions = objectMapper.readValue(this.fields.get("versions").toString(),new TypeReference<List<Version>>(){});
        if(this.fields.containsKey("votes") && !isNull(this.fields.get("votes")))
            this.votes = objectMapper.readValue(this.fields.get("votes").toString(),Votes.class);
        if(this.fields.containsKey("watches") && !isNull(this.fields.get("watches")))
            this.watches = objectMapper.readValue(this.fields.get("watches").toString(),Watches.class);
        if(this.fields.containsKey("worklog") && !isNull(this.fields.get("worklog"))){
            JSONObject worklog= objectMapper.readValue(this.fields.get("worklog").toString(), JSONObject.class);
            if(worklog!=null){
                if(worklog.containsKey("worklogs"))
                    this.workLogs = objectMapper.readValue(worklog.get("worklogs").toString(),new TypeReference<List<WorkLog>>(){});
            }
        }
        if(this.fields.containsKey("timeestimate") && !isNull(this.fields.get("timeestimate")))
            this.timeEstimate = objectMapper.readValue(this.fields.get("timeestimate").toString(),Integer.class);
        if(this.fields.containsKey("timespent") && !isNull(this.fields.get("timespent")))
            this.timeSpent = objectMapper.readValue(this.fields.get("timespent").toString(),Integer.class);
        if(this.fields.containsKey("created") && !isNull(this.fields.get("created"))){
            this.createdDate = JiraConverter.getDateTime((String) this.fields.get("created"));
        }
        if(this.fields.containsKey("updated") && !isNull(this.fields.get("updated"))) {
            this.updatedDate = JiraConverter.getDateTime((String) this.fields.get("updated"));
        }
        if(this.fields.containsKey("security") && !isNull(this.fields.get("security")))
            this.security = objectMapper.readValue(this.fields.get("security").toString(),Security.class);
    }

    private boolean isNull(Object object){
        if(object.equals(null))
            return true;
        else
            return false;
    }


}
