package com.item.lostandfound.handler;

import com.item.lostandfound.exceptions.InvalidFileException;
import com.item.lostandfound.exceptions.LostAndFoundException;
import com.item.lostandfound.exceptions.NoFileUploadedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class LostAndFoundExceptionHandler {

    @ExceptionHandler(value = NoFileUploadedException.class)
    public ResponseEntity<?> handleNoFileUploadException(NoFileUploadedException ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidFileException.class)
    public ResponseEntity<?> handleInvalidFileException(InvalidFileException ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = LostAndFoundException.class)
    public ResponseEntity<?> handleLostAndFoundException(LostAndFoundException ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
