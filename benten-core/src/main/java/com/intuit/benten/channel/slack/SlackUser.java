package com.intuit.benten.channel.slack;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class SlackUser {

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
        return "user="+this.name;
    }

}
