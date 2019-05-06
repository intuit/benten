package com.intuit.benten.jira.model.ghc;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class SprintReport extends GhResource {

    private Sprint sprint;
    private Contents contents;

    public Sprint getSprint() {
        return sprint;
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
    }

    public Contents getContents() {
        return contents;
    }

    public void setContents(Contents contents) {
        this.contents = contents;
    }
}
