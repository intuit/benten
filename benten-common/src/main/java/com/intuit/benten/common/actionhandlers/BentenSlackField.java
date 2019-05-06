package com.intuit.benten.common.actionhandlers;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class BentenSlackField {

    private String title;
    private String value;
    private boolean isShort;

    public BentenSlackField(String title, String value, boolean isShort) {
        this.title = title;
        this.value = value;
        this.isShort = isShort;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isShort() {
        return isShort;
    }

    public void setShort(boolean aShort) {
        isShort = aShort;
    }

}
