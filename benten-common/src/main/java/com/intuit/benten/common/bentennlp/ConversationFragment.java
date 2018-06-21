package com.intuit.benten.common.bentennlp;

import java.util.Map;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class ConversationFragment {

    private Question question;
    private Parameter parameter;
    private Answer answer;
    private Map<Integer,String> allowedValues;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public Map<Integer, String> getAllowedValues() {
        return allowedValues;
    }

    public void setAllowedValues(Map<Integer, String> allowedValues) {
        this.allowedValues = allowedValues;
    }
}
