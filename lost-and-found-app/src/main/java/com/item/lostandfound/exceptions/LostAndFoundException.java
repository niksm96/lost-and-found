package com.item.lostandfound.exceptions;

public class LostAndFoundException extends RuntimeException{
    public LostAndFoundException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
