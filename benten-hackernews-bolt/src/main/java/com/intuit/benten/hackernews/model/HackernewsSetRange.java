package com.intuit.benten.hackernews.model;

public class HackernewsSetRange {
    private Integer min;
    private Integer max;

    public HackernewsSetRange(Integer floor, Integer ceiling) {
        min = floor;
        max = ceiling;
    }

    public Integer getMin() { return min; }
    public void setMin(Integer min) { this.min = min; }
    public Integer getMax() { return max; }
    public void setMax(Integer max) { this.max = max; }
}
