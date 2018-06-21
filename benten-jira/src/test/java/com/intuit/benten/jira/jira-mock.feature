Feature: jira mock

  Background:
    * def stories = {}
    * def users = {}
    * def delay = function(){ java.lang.Thread.sleep(1000) }
    * def nextId = call read('increment.js')

  Scenario: pathMatches('/rest/api/2/search') && methodIs('get')
    * eval jql = requestParams.jql
    * def assignee = undefined
    * eval
      """
        var regex = /assignee=\s*(.*?)\s*ORDER BY updated DESC/g;
        assignee  = regex.exec(jql);
      """
    * def response = {}
    * def issues = []
    * eval userStories = stories
    * def fun = function(){ var filteredIssues = {} ; filteredIssues.issues = []; for(key in stories){ if(stories[key] != undefined && stories[key].fields != undefined && stories[key].fields.assignee!=undefined )if(stories[key].fields.assignee.name == assignee[1]){filteredIssues.issues.push(stories[key])}} return filteredIssues;}
    * eval filteredIssues = fun()
    * print filteredIssues
    * def response = filteredIssues
    * def afterScenario = delay

  Scenario: pathMatches('/rest/api/2/issue/{id}/transitions') && methodIs('get')
    * def responseStatus = 200
    * def response = read('transitions.json')
    * def afterScenario = delay


  Scenario: methodIs('post') && pathMatches('/rest/api/2/issue/{id}/transitions')
    * eval key = pathParams.id
    * def story = stories[key]
    * def transition = request.transition
    * eval
        """
         if(story)
         {
            if(transition){
                story.fields.status.name = transition.to.name;
            }
         }
        """
    * def responseStatus = story ? 204 : 404
    * def response = story ? "" : {"errorMessages":["Issue Does Not Exist"],"errors":{}}
    * def afterScenario = delay

  Scenario: pathMatches('/rest/api/2/issue/{id}/comment') && methodIs('post')
    * eval key = pathParams.id
    * def story = stories[key]
    * def comment = request
    * eval
        """
         if(story)
         {
            if(!story.fields.comment)
            {
                story.fields.comment = {}
                story.fields.comment.comments = [];
            }
            story.fields.comment.comments.push(comment);
         }
        """
    * def responseStatus = story ? 201 : 404
    * def response = story ? "" : {"errorMessages":["Issue Does Not Exist"],"errors":{}}
    * def afterScenario = delay


  Scenario: pathMatches('/rest/api/2/issue/{id}') && methodIs('put')
    * print hello
    * def responseStatus = 204
    * eval key = pathParams.id
    * def story = stories[key]
    * eval
        """
         if(story) {
            if(request.fields.assignee) {
                story.fields.assignee = request.fields.assignee;
                story.fields.assignee.displayName = request.fields.assignee.name;
            }
            if(request.fields.reporter){
                story.fields.reporter = request.fields.reporter;
                story.fields.reporter.displayName = request.fields.reporter.name;
            }
          }
        """
    * print story
    * def responseStatus = story ? 204 : 404
    * def response = story ? story : {"errorMessages":["Issue Does Not Exist"],"errors":{}}
    * def afterScenario = delay

  Scenario: pathMatches('/rest/api/2/issue/createmeta') && methodIs('get')
    * def response = read('create-metadata-response.json')
    * eval response.projects[0].key = requestParams.projectKeys[0]
    * def afterScenario = delay

  Scenario: pathMatches('/rest/api/2/issue/{id}') && methodIs('get')
    * eval key = pathParams.id
    * def story = stories[key]
    * print story
    * def responseStatus = story ? 200 : 404
    * def response = story ? story : {"errorMessages":["Issue Does Not Exist"],"errors":{}}
    * def afterScenario = delay

  Scenario: pathMatches('/rest/api/2/issue') && methodIs('post')
    * eval key = request.fields.project.key+'-'+nextId()
    * def story = { key : '#(key)',fields : {  priority : {name : 'P2:MEDIUM'}, status : {name : 'New'}, issuetype : '#(request.fields.issuetype)', description: '#(request.fields.description)', summary : '#(request.fields.summary)'}}
    * eval stories[key] = story
    * print stories
    * def response = story
    * def responseStatus = 201
    * def afterScenario = delay

  Scenario: pathMatches('/rest/api/2/issue/{id}/worklog') && methodIs('post')
    * eval key = pathParams.id
    * print 'here' + key
    * def story = stories[key]
    * def worklog = request
    * print 'here' + story
    * eval
        """
         if(story) {
            if(!story.fields.worklog){
                story.fields.worklog = {};
                story.fields.worklog.worklogs= [];
            }

            story.fields.worklog.worklogs.push(worklog);
         }
        """
    * def responseStatus = story ? 201 : 404
    * def response = story ? story : {"errorMessages":["Issue Does Not Exist"],"errors":{}}
    * def afterScenario = delay

  Scenario: pathMatches('/rest/agile/1.0/sprint/{id}/issue')
    * def response = read('sprint-issues-get-response.json')
    * def afterScenario = delay

  Scenario: pathMatches('/rest/agile/1.0/board')
    * def response = read('board-get-response.json')
    * def afterScenario = delay

  Scenario: pathMatches('/rest/greenhopper/1.0/sprintquery/{id}')
    * def response = read('sprint-by-boardid.json')
    * def afterScenario = delay

  Scenario: pathMatches('/rest/greenhopper/1.0/rapid/charts/sprintreport')
    * def response = read('get-sprint-report.json')
    * def afterScenario = delay

  Scenario: pathMatches('/browse/{id}') && methodIs('get')
    * eval key = pathParams.id
    * def story = stories[key]
    * def responseStatus = story ? 200 : 404
    * def response = story ? story : {"errorMessages":["Issue Does Not Exist"],"errors":{}}



  Scenario:
    * print request
    * def response = {}

