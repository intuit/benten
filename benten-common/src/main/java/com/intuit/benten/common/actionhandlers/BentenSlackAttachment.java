package com.intuit.benten.common.actionhandlers;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class BentenSlackAttachment {

    protected String title;
    protected String text;
    protected String color;
    protected List<BentenSlackField> bentenSlackFields = new ArrayList<>();
    protected String pretext;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<BentenSlackField> getBentenSlackFields() {
        return bentenSlackFields;
    }

    public void setBentenSlackFields(List<BentenSlackField> bentenSlackFields) {
        this.bentenSlackFields = bentenSlackFields;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getPretext() {
        return pretext;
    }

    public void setPretext(String pretext) {
        this.pretext = pretext;
    }

    public void addBentenSlackField(BentenSlackField bentenSlackField){
        bentenSlackFields.add(bentenSlackField);
    }


}
