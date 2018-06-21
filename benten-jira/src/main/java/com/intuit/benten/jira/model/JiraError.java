package com.intuit.benten.jira.model;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class JiraError {

    private List<String> errorMessages;
    private JSONObject errors;


    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List errorMessages) {
        this.errorMessages = errorMessages;
    }

    public JSONObject getErrors() {
        return errors;
    }

    public void setErrors(JSONObject jsonObject) {
        this.errors = jsonObject;
    }
}
