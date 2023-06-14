package com.mustafak01.locationservice.controller;

import com.mustafak01.locationservice.dto.PlacesDtoAndQueriedPlace;
import com.mustafak01.locationservice.dto.QueriedPlaceDto;
import com.mustafak01.locationservice.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/location-service")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping("/test")
    public String test(){
        return "It is working!!";
    }

    @GetMapping("/getNearbyPlaces")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public PlacesDtoAndQueriedPlace findNearbyPlaces(
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude,
            @RequestParam("radius") int radius) {
        return null;
    }

    @GetMapping("/getPlaceByLocation")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public QueriedPlaceDto getPlaceByLocation(
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude) {
        return this.locationService.getPlaceByLocation(latitude,longitude);
    }


}
