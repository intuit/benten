package com.intuit.benten.jira.model.ghc;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class EstimateSum extends GhResource{

    private Double value = null;
    private String text = null;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
