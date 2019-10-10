package com.intuit.benten.hackernews.exceptions;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class ImageGenerationException extends RuntimeException{
    public ImageGenerationException(){

    }
    public ImageGenerationException(String message){
        super(message);
    }
}
