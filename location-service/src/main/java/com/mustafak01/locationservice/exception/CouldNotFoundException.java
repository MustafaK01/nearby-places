package com.mustafak01.locationservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CouldNotFoundException extends RuntimeException{
    public CouldNotFoundException(){
        super("Couldn't found place or location");
    }
}
