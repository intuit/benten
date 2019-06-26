package com.intuit.benten.splunk.util;

public class BasicTimeDuration {
    private int val;
    private String unit;

    public BasicTimeDuration(){
        this.val = 4;
        this.unit = "h";
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
