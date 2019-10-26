package com.intuit.benten.common.actionhandlers;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class BentenSlackAttachment {

    protected String title;
    protected String title_link;
    protected String text;
    protected String color;
    protected String image_url;
    protected String thumb_url;
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

    public String getTitle_link() {
        return title_link;
    }

    public void setTitle_link(String title_link) {
        this.title_link = title_link;
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

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getThumb_url() {
        return thumb_url;
    }

    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }
}
