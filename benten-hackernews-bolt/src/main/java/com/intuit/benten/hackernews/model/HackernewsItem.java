package com.intuit.benten.hackernews.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 * Created by jleveroni on 10/09/2019
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HackernewsItem {
    private Integer id;
    private boolean deleted;
    private String type;
    private String by;
    private String time;
    private String text;
    private boolean dead;
    private Integer parent;
    private Integer poll;
    private List<Integer> kids;
    private String url;
    private Integer score;
    private String title;
    private List<Integer> parts;
    private Integer descendants;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getBy() { return by; }
    public void setBy(String by) { this.by = by; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public boolean isDead() { return dead; }
    public void setDead(boolean dead) { this.dead = dead; }

    public Integer getParent() { return parent; }
    public void setParent(Integer parent) { this.parent = parent; }

    public Integer getPoll() { return poll; }
    public void setPoll(Integer poll) { this.poll = poll; }

    public List<Integer> getKids() { return kids; }
    public void setKids(List<Integer> kids) { this.kids = kids; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public List<Integer> getParts() { return parts; }
    public void setParts(List<Integer> parts) { this.parts = parts; }

    public Integer getDescendants() { return descendants; }
    public void setDescendants(Integer descendants) { this.descendants = descendants; }
}
