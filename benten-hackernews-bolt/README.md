# Hackernews Bolt

## Overview
This bolt provides basic integration with the Hackernews API. The api itself is not very robust and
is a little unusual when it comes to standard apis. The data that Hackernews exposes is basically just 
a dump of their in-memory data structures into a firebase firestore database, more info 
[here](https://github.com/HackerNews/API).

## Local Development
To build and run with the Hackernews Bolt active, go through the DialogFlow and Slack setup make sure you have an active benten slack bot running.
Then run the following commands.
 
1. `cd ~/dev/benten/hackernews-bolt;`
2. `mvn install:install-file -Dfile=/Users/jleveroni/dev/benten/benten-hackernews-bolt/target/benten-hackernews-bolt-0.1.5.jar -DgroupId=benten-hackernews-bolt -DartifactId=benten-hackernews-bolt -Dversion=0.1.5 -Dpackaging=jar -DgeneratePom=true;`
3. `mvn clean install -Dmaven.test.skip=true;`
4. `cd ~/dev/benten/benten-starter;`
5. `mvn clean install -Dmaven.test.skip=true`
6. `mvn spring-boot:run;`

## Setup
To add the hackernews bolt to your project add the following to your pom file under the <dependencies> tag:

    <dependency>
        <groupId>benten-hackernews-bolt</groupId>
        <artifactId>benten-hackernews-bolt</artifactId>
        <version>${benten.version}</version>
    </dependency>

In the benten.properties file you need to be sure that the following properties are defined:

    # Dialogflow api token
    benten.ai.token=<your-dialogflow-token>
    
    # Slack bot token
    benten.slack.token=<your-slack-token>
    
    # Benten Hacker News configuration
    benten.hackernews.baseUrl=https://hacker-news.firebaseio.com
    benten.hackernews.apiVersion=v0


## Actions
This bolt exposes the following actions that can be triggered via DialogFlow Intents.

### Get Top Stories
**Action Name:** action_hackernews_get_top_stories

**Parameters:**

- _limit_: used to calculate the desired number of items returned, defaults to 10, max limit is 25 at the moment.
- _offset_: used to calculate the starting index of the items requested, defaults to 0.

**Result:** A list of stories rendered in a slack response message.

### Get New Stories
**Action Name:** action_hackernews_get_new_stories

**Parameters:**
- _limit_: used to calculate the desired number of items returned, defaults to 10, max limit is 25 at the moment.
- _offset_: used to calculate the starting index of the items requested, defaults to 0.

**Result:** A list of stories rendered in a slack response message.

### Get Best Stories
**Action Name:** action_hackernews_get_best_stories

**Parameters:**
- _limit_: used to calculate the desired number of items returned, defaults to 10, max limit is 25 at the moment.
- _offset_: used to calculate the starting index of the items requested, defaults to 0.

**Result:** A list of stories rendered in a slack response message.

### Get Latest Asks
**Action Name:** action_hackernews_latest_asks

**Parameters:**
- _limit_: used to calculate the desired number of items returned, defaults to 10, max limit is 25 at the moment.
- _offset_: used to calculate the starting index of the items requested, defaults to 0.

**Result:** A list of stories rendered in a slack response message.

### Get Latest Show Stories
**Action Name:** action_hackernews_get_latest_show_stories

**Parameters:**
- _limit_: used to calculate the desired number of items returned, defaults to 10, max limit is 25 at the moment.
- _offset_: used to calculate the starting index of the items requested, defaults to 0.

**Result:** A list of stories rendered in a slack response message.

### Get Latest Job Stories
**Action Name:** action_hackernews_get_latest_job_stories

**Parameters:**
- _limit_: used to calculate the desired number of items returned, defaults to 10, max limit is 25 at the moment.
- _offset_: used to calculate the starting index of the items requested, defaults to 0.

**Result:** A list of stories rendered in a slack response message.

#### Get The Last Posted Content
**Action Name:** action_hackernews_newest_content_id

**Parameters:** None

**Result:** The most recently posted items id.
