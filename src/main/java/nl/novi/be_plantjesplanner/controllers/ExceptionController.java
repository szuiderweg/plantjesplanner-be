package nl.novi.be_plantjesplanner.controllers;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import nl.novi.be_plantjesplanner.exceptions.DuplicateResourceException;
import nl.novi.be_plantjesplanner.exceptions.InvalidImageTypeException;
import nl.novi.be_plantjesplanner.exceptions.RecordNotFoundException;
import nl.novi.be_plantjesplanner.exceptions.UnreadableFileException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionController {
    //HTTP 400-errors

    @ExceptionHandler(HttpMessageNotReadableException.class)//handles Http requests with bad JSON
    public ResponseEntity<String> handleInvalidJson(HttpMessageNotReadableException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ongeldig request body "+e.getMostSpecificCause().getMessage());
    }

    @ExceptionHandler(DuplicateResourceException.class)//handles error when trying to duplicate something that should be unique
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
//    @ExceptionHandler(MethodArgumentNotValidException.class)//handle errors thrown by @valid annotation in DTO's. limitation: only one error per DTO field
//    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
//        Map<String, String> errors = new HashMap<>();
//        for (FieldError error : ex.getFieldErrors()) {
//            errors.putIfAbsent(error.getField(), error.getDefaultMessage());
//        }
//        return ResponseEntity.badRequest().body(errors);
//    }
//    @ExceptionHandler(ConstraintViolationException.class)//handles errors Thrown by @Validated annotation in controllers in case of invalid requestparameters. collect all violations in a Map with the field name and error message as key-value pairs .
//    //limitation: only one error message per parameter
//    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
//        Map<String, String> errors = new HashMap<>();
//
//        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
//            errors.putIfAbsent(violation.getPropertyPath().toString(), violation.getMessage());
//        }
//        return ResponseEntity.badRequest().body(errors);
//    }


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

    //handles exceptions related to invalid user credentials
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Ongeldige inloggegevens");
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
