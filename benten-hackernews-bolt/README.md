# Hackernews Bolt

## Overview
This bolt provides basic integration with the Hackernews API. The api itself is not very robust and
is a little unusual when it comes to standard apis. The data that Hackernews exposes is basically just 
a dump of their in-memory data structures into a firebase firestore database, more info 
[here](https://github.com/HackerNews/API).

Hackernews's API is only capable of returning a single item via its `id` or a list of `id`s that you can then use to
query for said `id`s content. Because of the nature of this API I implemented a multithreaded solution that will
parallelize multiple queries to fetch items from the API via their `id`s. Ideally this service could be abstrscted and
made useful to any and all other bolts in this project.

## Contextual Information (Definitions)
- Collection: A list or array of one of the core types of content that hackernews' api returns.
I.e a list of `topstories`, `newstories`, `beststories`, `askstories`, `showstories`, `jobstories`.
For more information including how objects are structured in Hackernews's API please see 
their [API](https://github.com/HackerNews/API). for more information

## Local Development
To build and run with the Hackernews Bolt active, go through the DialogFlow and Slack setup make sure you have an active benten slack bot running.
Then run the following commands.
 
1. `cd ~/dev/benten/hackernews-bolt;`
2. `mvn clean install`
3. `cd ~/dev/benten/benten-starter;`
5. `mvn clean install -Dmaven.test.skip=true`
6. `mvn spring-boot:run;`

## Testing
Initially tests has been provided for every public method exposed in the Hackernews Bolt. If Hackernews
alters their api and you choose to extend/alter the functionality of this bolt, please make sure to 
include tests for all public methods you change or add. To run the tests for this bolt use the following
commands, they will run all tests under the `/benten/benten-hackernews-bolt/src/test` folder.

1. `cd ~/dev/benten/hackernews-bolt;`
2. `mvn clean install;`

## Integrating Hackernews Bolt Into Your Project
To add the hackernews bolt to your project add the following to your pom file under the <dependencies> tag:

Maven:

    <dependency>
        <groupId>benten-hackernews-bolt</groupId>
        <artifactId>benten-hackernews-bolt</artifactId>
        <version>${benten.version}</version>
    </dependency>

Gradle:

    TODO <insert gradle equivalent here>


In the benten.properties file you need to be sure that the following properties are defined:

    # Dialogflow api token
    benten.ai.token=<your-dialogflow-token>
    
    # Slack bot token
    benten.slack.token=<your-slack-token>
    
    # Benten Hacker News configuration
    benten.hackernews.baseUrl=https://hacker-news.firebaseio.com
    benten.hackernews.apiVersion=v0
    
Your application should now support the actions listed below when they are sent from DialogFlow. 


## Actions
This bolt exposes the following actions that can be triggered via DialogFlow Intents.

### Get Top Stories
**Action Name:** `action_hackernews_get_top_stories`

**Description:** Retrieves the top stories at that time.

**Parameters:**

- _limit_: used to calculate the desired number of items returned, defaults to 10, max limit is 25 at the moment.
- _offset (optional)_ : used to calculate the startIndex based on the resultSetSize.
- _startIndex (optional)_ : used to calculate the start index where the resultSetSize will start from.
 
**Result:** A list of stories rendered in a slack response message.

### Get New Stories
**Action Name:** `action_hackernews_get_new_stories`

**Description:** Retrieves the new stories at that time.

**Parameters:**
- _limit_: used to calculate the desired number of items returned, defaults to 10, max limit is 25 at the moment.
- _offset (optional)_ : used to calculate the startIndex based on the resultSetSize. Defaults to 0, overrides startIndex.
- _startIndex (optional)_ : used to calculate the start index where the resultSetSize will start from.

**Result:** A list of stories rendered in a slack response message.

### Get Best Stories
**Action Name:** `action_hackernews_get_best_stories`

**Description:** Retrieves the best stories at that time.

**Parameters:**
- _limit_: used to calculate the desired number of items returned, defaults to 10, max limit is 25 at the moment.
- _offset (optional)_ : used to calculate the startIndex based on the resultSetSize. Defaults to 0, overrides startIndex.
- _startIndex (optional)_ : used to calculate the start index where the resultSetSize will start from.

**Result:** A list of stories rendered in a slack response message.

### Get Latest Asks
**Action Name:** `action_hackernews_latest_asks`

**Description:** Retrieves the latest ask stories at that time.

**Parameters:**
- _limit_: used to calculate the desired number of items returned, defaults to 10, max limit is 25 at the moment.
- _offset (optional)_ : used to calculate the startIndex based on the resultSetSize. Defaults to 0, overrides startIndex.
- _startIndex (optional)_ : used to calculate the start index where the resultSetSize will start from.

**Result:** A list of stories rendered in a slack response message.

### Get Latest Show Stories
**Action Name:** `action_hackernews_get_latest_show_stories`

**Description:** Retrieves the latest show stories at that time.

**Parameters:**
- _limit_: used to calculate the desired number of items returned, defaults to 10, max limit is 25 at the moment.
- _offset (optional)_ : used to calculate the startIndex based on the resultSetSize. Defaults to 0, overrides startIndex.
- _startIndex (optional)_ : used to calculate the start index where the resultSetSize will start from.

**Result:** A list of stories rendered in a slack response message.

### Get Latest Job Stories
**Action Name:** `action_hackernews_get_latest_job_stories`

**Description:** Retrieves the latest show job at that time.

**Parameters:**
- _limit_: used to calculate the desired number of items returned, defaults to 10, max limit is 25 at the moment.
- _offset (optional)_ : used to calculate the startIndex based on the resultSetSize. Defaults to 0, overrides startIndex.
- _startIndex (optional)_ : used to calculate the start index where the resultSetSize will start from.

**Result:** A list of stories rendered in a slack response message.

#### Parameter Explanation

There are 3 parameters to all of the listed queries above, `resultSetSize`, `offset`, `startIndex`.
All of these parameters have default values so if you do not specify values for any of them they will 
default to `10`, `0`, and `0` respectively. Note that the calculations for determining the result of a
collection query depend on either `offset` or `startIndex` being defined, because of this the values are defaulted.
If you do not provide both parameters, the calculations will be based off the provided `resultSetSize` (or default value of `10`)
and an `offset` of `0`. The usages for `offset` or `startIndex` are explained more in depth in the following sections.

_resultSetSize_

The `resultSetSize` represents the number of items you wish to be returned when you query a collection.
For example, say you want the latest 13 "stories" from Hackernews. To achieve this you would want
to send the `benten-hackernews-bolt` an action with a name of `action_hackernews_get_new_stories` 
and a parameter of `resultSetSize` that is equal to `13`, and `offset` of 0. If no `resultSetSize` parameter
is specified it will default to `10`. **IMPORTANT** `resultSetSize` is currently also limited to 25 as its
maximum value due to the structure of the hackernews API and the use of multi-threading.

_offset_

The `offset` represents the offset from the latest content in units of whatever the `limit` is set to.
For example, say out of the last 100 `beststories` on Hackernews you want to retrieve the stories 
between `40 - 60`. You would send a `action_hackernews_get_new_stories` action with a `resultSetSize` 
of `20` and an `offset` of `2`. 

Calculations:

    startIndex = ((offset` * resultSetSize)     // Evaluation (2 * 20) + 1     = 41
    endIndex =  startIndex` + `resultSetSize    // Evaluation 40 + 20          = 60
    resultSetIndexRange = [41, 60)
    
The set of stories returned is exclusive of the min boundary and inclusive of the max i.e. 
`[startIndex, endIndex)`.

Note if the calculated `(startIndex + endIndex) - 1` is greater than the length of the array of ids
returned by the Hackernews API you will get an error, (API usually returns between 400 and 500 (max) ids).

_startIndex_

Say you want to get more granular control over the result set size of a Hackernews collection query. 
This is where `startIndex` comes in. Let's say you want to query from the last 100 stories starting 
at index 17 and you only want 3 results. This would not be possible using `offset` as you could 
specify offset of 0 but that would still give you the results 17 - 26. In this scenario you should
not supply a value for `offset` and instead supply a value of `17` to the `startIndex` parameter
and a value of `3` to the `resultSetSize`. This will give you the result set of the latest stories
starting at the 17th last story through the last 19th story.

This gives you more granular control over your search results. However remember that `offset` always 
takes precedence over `startIndex` so if you want to use `startIndex` make sure to not set the 
`offset` parameter.   


#### Example DialogFlow Payloads

No resultSetSize or offset provided

    paylod: {
        action: "action_hackernews_get_top_stories"
    }
    
    result: {
        // result of the latest 10 top stories
        items: [{...}]
    }
    
Valid resultSetSize specified

    paylod: {
        action: "action_hackernews_get_top_stories",
        resultSetSize: 7
    }
    
    result: {
        // result of the 7 latest top stories
        items: [{...}]
    }
    
Valid resultSetSize and offset specified

    paylod: {
        action: "action_hackernews_get_top_stories",
        resultSetSize: 7,
        offset: 4
    }
    
    result: {
        // result of the [28, 35) latest top stories
        items: [{...}]
    }

Valid resultSetSize and startIndex specified

    paylod: {
        action: "action_hackernews_get_top_stories",
        resultSetSize: 20,
        startIndex: 5
    }
    
    result: {
        // result of the [5, 30) latest top stories
        items: [{...}]
    }
    
Valid resultSetSize, offset, and startIndex specified

    paylod: {
        action: "action_hackernews_get_top_stories",
        resultSetSize: 5,
        offset: 5
        startIndex: 10
    }
    
    result: {
        // result of the [25, 30) latest top stories
        // Note that startIndex is overriden in this case
        items: [{...}]
    }
