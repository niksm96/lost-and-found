package com.item.lostandfound.exceptions;

/**
 * InvalidFileException is a custom exception extending the parent Exception class.
 */
public class InvalidFileException extends Exception {
    public InvalidFileException(String errorMessage) {
        super(errorMessage);
    }
}
