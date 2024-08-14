package com.item.lostandfound.exceptions;

/**
 * InvalidFileException is a custom exception extending the parent RunTimeException class.
 */
public class InvalidFileException extends RuntimeException {
    public InvalidFileException(String errorMessage) {
        super(errorMessage);
    }

    public InvalidFileException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

    public InvalidFileException(Throwable cause) {
        super(cause);
    }
}
