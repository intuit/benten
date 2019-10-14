package com.intuit.benten.hackernews.exceptions;

/**
 * Created by jleveroni on 10/09/2019
 */
public class BentenHackernewsException extends RuntimeException {
    public BentenHackernewsException(String hackerNewsMessage) {
        super(hackerNewsMessage);
    }

    public BentenHackernewsException(String hackerNewsMessage, String message) {
        super("[BentenHackernewsException] " + hackerNewsMessage +  " " + message);
    }
}
