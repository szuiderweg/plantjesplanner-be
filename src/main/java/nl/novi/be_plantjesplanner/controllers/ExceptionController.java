package nl.novi.be_plantjesplanner.controllers;

import nl.novi.be_plantjesplanner.exceptions.DuplicateResourceException;
import nl.novi.be_plantjesplanner.exceptions.InvalidImageTypeException;
import nl.novi.be_plantjesplanner.exceptions.RecordNotFoundException;
import nl.novi.be_plantjesplanner.exceptions.UnreadableFileException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.io.IOException;
import java.sql.PreparedStatement;

@ControllerAdvice
public class ExceptionController {
    //HTTP 400-errors

    @ExceptionHandler(HttpMessageNotReadableException.class)//handles Http requests with bad JSON
    public ResponseEntity<String> handleInvalidJson(HttpMessageNotReadableException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ongeldig request body "+e.getMostSpecificCause().getMessage());
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<String> handleDuplicateResourceException(DuplicateResourceException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(RecordNotFoundException.class)//custom recordNotFound exception
    public ResponseEntity<Object> handleRecordNotFoundException(RecordNotFoundException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)//response for invalid user requests
        public ResponseEntity<String> handleIllegalInputException(IllegalArgumentException e){
               return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("dit verzoek klopt niet helemaal: \n"+e.getMessage());
    }


    //handles the inbuilt Springboot MaxUploadSizeExceededException in an HTTP response
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxSizeException(MaxUploadSizeExceededException e) {
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                .body("Het bestand is te groot: toegestane grootte is maximaal 2MB.");
    }

    //handles exception related to uploading files with unsupported MIME-types
    @ExceptionHandler(InvalidImageTypeException.class)
    public ResponseEntity<String> handleInvalidImageTypeException(InvalidImageTypeException e) {
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(e.getMessage());
    }

    //handles exception related to unreadable files. In case a file is of a supported MIMEtype, but has become unreadable somehow
    @ExceptionHandler(UnreadableFileException.class)
    public ResponseEntity<String> handleUnreadableFileException(UnreadableFileException e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    //500 errors for development
    //IOexception
    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    //general runtime exception
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Er is een of andere vage fout opgetreden. Sorry!");
    }
}
