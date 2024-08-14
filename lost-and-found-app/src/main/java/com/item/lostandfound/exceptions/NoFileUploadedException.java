package com.item.lostandfound.exceptions;

import java.io.FileNotFoundException;

public class NoFileUploadedException extends FileNotFoundException {

    public NoFileUploadedException() {
        super();
    }
    public NoFileUploadedException(String message) {
        super(message);
    }
}
