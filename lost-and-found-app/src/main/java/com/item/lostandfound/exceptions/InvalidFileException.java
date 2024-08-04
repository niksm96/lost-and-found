package com.item.lostandfound.exceptions;

import java.io.FileNotFoundException;

public class InvalidFileException extends Exception {
    public InvalidFileException(String errorMessage) {
        super(errorMessage);
    }
}
