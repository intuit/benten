# <img src="benten-core/src/main/resources/benten.png" height="42" width="42"/> BenTen
## CUI Chatbot framework (Has out of the box `slack-bot` support for `Jira` and `Jenkins`)

[![Build Status](https://travis-ci.org/intuit/benten.svg?branch=master)](https://travis-ci.org/intuit/benten)
[![Support Slack](https://img.shields.io/badge/support-slack-red.svg)](https://join.slack.com/t/bentenapp/shared_invite/enQtMzg3MTkwNjc1MDkxLWI2ZDkzZjVhNTk2ZThlMjRhNTg3ZTc5OGExYTk1MGZkYTQ3Yzg3NzAzYWQyYzM5ZThiMGE2Mjk2OGZlOWUzYzk)
[![License](https://img.shields.io/github/license/intuit/benten.svg)](https://github.com/intuit/benten)

BenTen is a CUI Chatbot framework that provides all the integrations that are necessary for building useful conversational chatbots in a few minutes. It has integrations with NLP engines like [Dialogflow](https://dialogflow.com/), Messaging platforms like [Slack](https://slack.com/), project management tools like [Jira](www.atlassian.com/Jira) and Continuous Integration Tools like [Jenkins](https://jenkins.io/).
It also has support to render messages in various formats like Slack, html (html to image conversion), csv etc...

The frame work lets you concentrate on the `core` functionality you want to build (for example building a conversational chatbot to monitor application health and show it in slack on demand) rather than worry about integrations with slack, NLP engines or applications like Jira, Jenkins and the orchestration between these systems.

## Story behind the name `BenTen`
In case you were wondering why the name BenTen! You can find an answer here [Why the name Benten ?](https://en.wikipedia.org/wiki/Benzaiten)

## Features
|Jira Feature|Command  |
|--|--|
|Create new JIRA Story/Bug/Sub-Task  |```create jira story``` , ```create jira bug```, ```create jira subtask```, ```create jira story Experience benten jira integration project BENTEN```  |
|Show details of a JIRA issue  |```details of issue```, ```details of issue BENTEN-1```  |
|Assign issue  |```assign issue BENTEN-1 to @divakarungatla```  |
|Search issues assigned to me or created by me  |```my issues```, ```my tickets```  |
|Comment on an issue  |```comment on issue```, ```comment on issue BENTEN-1 this is an example comment```  |
|Log work for an issue/story  |```log 3h 4m against issue BENTEN-1 this is a comment for logging work```  |
|Show subtasks of a story  |```subtasks of story AAA-1234```  |
|Transition issue  |```move BENTEN-1 to inprogress```  |
|Cycle time for a sprint  |```cycle time of board Benten over last 2 sprints```  |
|Velocity of a team based on a sprint  |```velocity of board Benten over last 2 sprints```  |

**_Note:_** BenTen uses a headless user which you are going to specify in the benten properties file in the following sections to perform the jira operations. So some features like `assign` `move` will need the headless user to be added as admin to you projects. Here is how you do it [Add user as admin in Jira](https://confluence.atlassian.com/jiracoreserver073/managing-project-role-memberships-861257122.html).

|Jenkins Feature|Command  |
|--|--|
|Search for Jenkins job  |```search for jenkins job```, ```search for jenkins job with prefix Purchase```  |
|Details of Jenkins job  |```details of jenkins job```, ```details of jenkins job Purchase-Service-Release```  |
|Build Jenkins job  |```build jenkins job Purchase-Service-Release```  |

## Try it out!
Before moving forward just experience the awesomeness of BenTen for yourself! Just follow the below link to sign up.

[Join Benten Slack](https://join.slack.com/t/bentenapp/shared_invite/enQtMzg3MTkwNjc1MDkxLWI2ZDkzZjVhNTk2ZThlMjRhNTg3ZTc5OGExYTk1MGZkYTQ3Yzg3NzAzYWQyYzM5ZThiMGE2Mjk2OGZlOWUzYzk)

After signing up click on the below link which opens up the benten bot in slack. Type `help` and that should guide you.
My recommendation is to try `Create Jira Story` to see the conversational power and then `my issues`

[Open BenTen Bot](https://bentenapp.slack.com/messages/DAVB8E2KB/)

**_Note:_** The above bot runs against `jira and jenkins mocks` that are written using [Karate Test Doubles.](https://hackernoon.com/api-consumer-contract-tests-and-test-doubles-with-karate-72c30ea25c18)

<img src="https://github.com/DivakarUngatla/divakarungatla.github.io/blob/master/benten/open-benten.gif?raw=true" width=800 />

## Let's set up BenTen
Now that you have experienced the bot, let us set up BenTen with your `own` slack bot and run against your Jira and Jenkins.

> If you are behind a corporate proxy, or especially if your local Maven installation has been configured to point to a repository within your local network, the command below may not work. One workaround is to temporarily disable or rename your Maven [`settings.xml`](https://maven.apache.org/settings.html) file, and try again.


## Setting up BenTen from binaries (recommended if you want just use existing capabilites and add custom features only specific to your organization)

There is a maven archetype(benten-archetype) that I created that can be used to set up a skeleton project super quick. Run the below command in your termimal.
```sh
mvn archetype:generate -DarchetypeGroupId=com.intuit.benten -DarchetypeArtifactId=benten-archetype -DarchetypeVersion=0.1.5 -DgroupId=com.mycompany -DartifactId=benten-starter
cd benten-starter
mvn clean install -Dmaven.test.skip=true
```

## Setting up BenTen from sources (recommended if you want to contribute/add new features or integrations to BenTen )

To set up BenTen from sources follow the below steps
```sh
git clone https://github.com/intuit/benten
mvn clean install -Dmaven.test.skip=true
```
## To use BenTen in your existing spring project include the below dependencies in your projects pom.xml and refer to [Setting up](https://github.com/intuit/benten/blob/master/benten-starter/src/main/java/BentenStarterApplication.java) to initialize BenTen.

```xml
<dependency>
    <groupId>com.intuit.benten</groupId>
    <artifactId>benten-core</artifactId>
    <version>0.1.5</version>
</dependency>
```
#### Include Jira and Jenkins bolts

```xml
<dependency>
    <groupId>com.intuit.benten</groupId>
    <artifactId>benten-jira-bolt</artifactId>
    <version>0.1.5</version>
</dependency>
<dependency>
    <groupId>com.intuit.benten</groupId>
    <artifactId>benten-jenkins-bolt</artifactId>
    <version>0.1.5</version>
</dependency>
```

## Create a slack team and slackbot(You can skip this section if you already have a slack bot API token)

Follow the below steps to create a slack team and then a slack bot. You can skip this step if you already have a team and are the admin.
#### Creating Slack team
1. Open https://slack.com/
2. Provide your email ID. Select Create New workspace. 
3. Check your email and enter the code to verify your email.
4. Provide your name and set a password
5. Add some details to your team in the next page
6. Provide a company name
7. Team URL should be unique - Also remember this URL - this is what is used to login to your slack instance
8. Agree with the terms
9. Skip the invite step
10. You are up and running with your own instance of Slack.

Now that team is created, let us create a slack bot
#### Creating Slack bot
1. Open your {team-URL}/apps (the one you created above). Ex: https://test-visual.slack.com/apps
2. Search for bot in the search bar and select `bots`
3. In the bots landing page click on Add configuration
4. Provide a bot name. Ex: divakar-bot and click on Add Bot integration
5. In the Setup instruction page: `Copy and store the API Token`. Ex: xoxb-22672546-n1X9APk3D0tfksr81NJj6VAM
6. Save the integration

## Create your own [Dialog Flow Agent](https://dialogflow.com/)

You might have noticed the conversation interface of BenTen. Benten uses [Dialog Flow](https://dialogflow.com/) to build the conversations. You can read more about Dialog flow at https://dialogflow.com/docs/getting-started/basics.

Let us set up your own dialog flow agent. Download the agent zip file from here [benten-agent](https://github.com/DivakarUngatla/divakarungatla.github.io/raw/master/benten/dialogflow-agent/benten-open-source.zip)

Follow the instructions in this page [Create-BenTen-Agent-in-Dialog-flow](https://github.com/intuit/benten/wiki/Create-BenTen-Agent-in-Dialog-flow). 

At the end of it you should have your own `dialog flow token`.

Now open benten.properties

```sh
cd benten-starter
vi src/main/resources/benten.properties
```
In line #10 change the value of `benten.ai.token` with your token from above

# Start BenTen
```sh
cd benten-starter
mvn clean install -Dmaven.test.skip=true
vi src/main/resources/benten.properties
```
In line #11 replace `<slackbot token>` with your bot token from bot creation step above [Creating Slack bot](#creating-slack-bot)

```sh
mvn spring-boot:run
```
Now if you see your bot in Slack it should be active! Type `hi` to see it working. Type `help jira` or `create jira story` and you can see responses. 

## Point BenTen to your own Jira instance

Remember, at this point benten is still running against the karate mocks that come as part of the BenTen code/artifacts. Now let us point it to you  Jira instance. Follow the below steps
```sh
cd benten-starter
vi src/main/resources/benten.properties
```
In line #16 change `benten.jira.baseurl` url to point to your own jira instance instead of the localhost. Also change `benten.jira.username` and `benten.jira.password` accordingly. BenTen uses these credentials to connect to Jira. Save the changes.

```sh
cd benten-starter
vi src/main/resources/benten.properties
```
Restart your application
```
mvn clean install -Dmaven.test.skip=true
mvn spring-boot:run
```
Now try the jira commands from your slack-bot and it should work against your Jira!

If you need to use a proxy when you are on a corporate network update below proxy settings accordingly and BenTen will honor the proxy settings
```
#benten.proxy.port=<proxy-port>
#benten.proxy.host=<proxy-host>
#benten.proxy.protocol=http
```
## Point BenTen to your own Jenkins instance

Similar to Jira make changes for Jenkins as well in `benten.properties`.

```sh
cd benten-starter
vi src/main/resources/benten.properties
```
In line #18 change `benten.jenkins.baseurl` url to point to your own jenkins instance instead of the localhost. Also change `benten.jenkins.username` and `benten.jenkins.password` accordingly. BenTen uses these credentials to connect to Jira. Save the changes.

Restart your application
```
mvn clean install -Dmaven.test.skip=true
mvn spring-boot:run
```
Type `jenkins` in your bot and try any of the operations to see them work against your jenkins.

## Let's get to Action! (Adding a new ActionHandler)

Let us build a feature in BenTen now. Each feature is called an `action` in BenTen. Remember! the beauty of BenTen is that you need to concentrate only on the core logic. Let us see how.

We will build an `action` which will ping a service for `n` number of times and display the results in the slack-bot after every time it pings the application.

First let us build the conversation. Open your agent that you created in this [section](#create-your-own-dialog-flow-agent). Follow this wiki [Creating BenTen Example Conversation in Dialogflow]( https://github.com/intuit/benten/wiki/Create-BenTen-Example-conversation-in-Dialog-flow) 

Create a new package `com.example` in benten-starter.
```sh
cd benten-starter
mkdir com/example
```
Download [BentenExampleAction.java](https://github.com/intuit/benten/blob/master/benten-examples/src/main/java/com/example/BentenExampleAction.java) and copy it to the `com.example` package. The contents of the file are explained below!

# <img src="https://github.com/DivakarUngatla/divakarungatla.github.io/blob/master/benten/benten_actionhandler.png?raw=true" width="2040px"/> 

Restart your application. 
```
mvn clean install -Dmaven.test.skip=true
mvn spring-boot:run
```

Open your slack bot. Type `ping benten for 5 times` and you should be able to see the below output

<img src="https://github.com/DivakarUngatla/divakarungatla.github.io/blob/master/benten/benten-example.gif?raw=true" width=800 />

If you reached this point it might have stuck you that what you can build using BenTen is only limited by your thoughts! Get creative!
