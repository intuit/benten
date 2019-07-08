package com.intuit.benten.splunk.util;

public class BasicTimeDurationHelper {
    public static String timeDurationForSearch(BasicTimeDuration basicTimeDuration) {
        return "-" + basicTimeDuration.getVal() + basicTimeDuration.getUnit() + "@" + basicTimeDuration.getUnit();
    }
}
