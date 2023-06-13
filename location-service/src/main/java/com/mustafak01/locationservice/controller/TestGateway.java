package com.mustafak01.locationservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/internal-location-service")
@RequiredArgsConstructor
public class TestGateway {

    @GetMapping("/test")
    public String test(){
        return "It is working!!";
    }

}
