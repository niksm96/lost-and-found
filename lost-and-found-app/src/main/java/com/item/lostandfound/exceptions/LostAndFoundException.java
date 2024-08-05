package com.item.lostandfound.exceptions;

/**
 * LostAndFoundException is a custom exception extending the RuntimeException class.
 */
public class LostAndFoundException extends RuntimeException{
    public LostAndFoundException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
