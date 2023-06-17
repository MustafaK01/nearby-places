package com.mustafak01.locationservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class MissingDataException extends RuntimeException{
    public MissingDataException(){
        super("Missing Data");
    }
}
