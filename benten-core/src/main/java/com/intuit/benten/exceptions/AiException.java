package com.intuit.benten.exceptions;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class AiException extends RuntimeException {

    public AiException(){

    }

    public AiException(String message){
        super(message);
    }
}
