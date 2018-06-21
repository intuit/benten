package com.intuit.benten.jira.model.ghc;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class EstimateStatistic {

    private String statFieldId;
    private StatField statFieldValue;

    public String getStatFieldId() {
        return statFieldId;
    }

    public void setStatFieldId(String statFieldId) {
        this.statFieldId = statFieldId;
    }

    public StatField getStatFieldValue() {
        return statFieldValue;
    }

    public void setStatFieldValue(StatField statField) {
        this.statFieldValue = statField;
    }
}

