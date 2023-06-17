package com.mustafak01.locationservice.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(x->{
            String fieldName = ((FieldError)x).getField();
            String errorMsg = x.getDefaultMessage();
            errors.put(fieldName,errorMsg);
        });
        return ResponseEntity.badRequest().body(errors);
    }


    @ExceptionHandler(CouldNotFoundException.class)
    public ResponseEntity<?> couldNotFoundException(CouldNotFoundException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                this.adjustMessages(e));
    }

    @ExceptionHandler(MissingDataException.class)
    public ResponseEntity<?> couldNotUpdateException(MissingDataException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                this.adjustMessages(e));
    }

    private Map<String, String> adjustMessages(RuntimeException exception){
        Map<String,String> errors = new HashMap<>();
        errors.put("msg",exception.getMessage());
        return errors;
    }


}
