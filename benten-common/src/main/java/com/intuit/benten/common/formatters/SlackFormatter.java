package com.intuit.benten.common.formatters;

import allbegray.slack.SlackTextBuilder;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class SlackFormatter {

    private SlackTextBuilder slackTextBuilder;
    private SlackFormatter (SlackTextBuilder slackTextBuilder){
        this.slackTextBuilder = slackTextBuilder;
    }
    public static SlackFormatter create() {
        SlackTextBuilder slackTextBuilder = SlackTextBuilder.create();
        return new SlackFormatter(slackTextBuilder);
    }

    public SlackFormatter text(String text){
        this.slackTextBuilder.text(text);
        this.slackTextBuilder.text(" ");
        return this;
    }

    public SlackFormatter mail(String email) {
        return this.mail(email, (String)null);
    }

    public SlackFormatter mail(String email, String text) {
        this.slackTextBuilder.link("mailto:" + email, text);
        return this;
    }

    public SlackFormatter link(String url) {
        this.slackTextBuilder.link(url, (String)null);
        return this;
    }

    public SlackFormatter link(String url, String text) {
        this.slackTextBuilder.link(url,text);
        this.slackTextBuilder.text(" ");
        return this;
    }

    public SlackFormatter bold(String text) {
        this.slackTextBuilder.bold(text);
        return this;
    }

    public SlackFormatter italic(String text) {
        this.slackTextBuilder.italic(text);
        return this;
    }

    public SlackFormatter strike(String text) {
        this.slackTextBuilder.strike(text);
        return this;
    }

    public SlackFormatter code(String code) {
        this.slackTextBuilder.code(code);
        this.slackTextBuilder.text(" ");
        return this;
    }

    public SlackFormatter preformatted(String text) {
        this.slackTextBuilder.preformatted(text);
        return this;
    }

    public SlackFormatter quote(String text) {
        this.slackTextBuilder.quote(text);
        return this;
    }

    public SlackFormatter paragraph(String text) {
        this.slackTextBuilder.paragraph(text);
        return this;
    }

    public SlackFormatter newline() {
       this.slackTextBuilder.newline();
        return this;
    }

    public String build() {
        return this.slackTextBuilder.build();
    }

}
