package nl.novi.be_plantjesplanner.controllers;

import nl.novi.be_plantjesplanner.exceptions.InvalidImageTypeException;
import nl.novi.be_plantjesplanner.exceptions.RecordNotFoundException;
import nl.novi.be_plantjesplanner.exceptions.UnreadableFileException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.io.IOException;

@ControllerAdvice
public class ExceptionController {
    //HTTP 400-errors
    @ExceptionHandler(value = RecordNotFoundException.class)//custom recordNotFound exception
    public ResponseEntity<Object> exception(RecordNotFoundException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)//response for invalid user requests
        public ResponseEntity<String> illegalInputException(IllegalArgumentException e){
               return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    //handles the inbuilt Springboot MaxUploadSizeExceededException in an HTTP response
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxSizeException(MaxUploadSizeExceededException e) {
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                .body("Het bestand is te groot: toegestane grootte is maximaal 2MB.");
    }

    //handles exception related to uploading files with unsupported MIME-types
    @ExceptionHandler(value = InvalidImageTypeException.class)
    public ResponseEntity<String> exception(InvalidImageTypeException e) {
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(e.getMessage());
    }

    //handles exception related to unreadable files. In case a file is of a supported MIMEtype, but has become unreadable somehow
    @ExceptionHandler(value = UnreadableFileException.class)
    public ResponseEntity<String> exception(UnreadableFileException e){
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
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Er is een of andere vage fout opgetreden");
    }
}
