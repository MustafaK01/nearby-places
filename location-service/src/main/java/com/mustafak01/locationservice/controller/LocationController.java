package com.mustafak01.locationservice.controller;

import com.mustafak01.locationservice.dto.PlacesDtoAndQueriedPlaceDto;
import com.mustafak01.locationservice.dto.QueriedPlaceDto;
import com.mustafak01.locationservice.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/location-service")
@RequiredArgsConstructor
@CrossOrigin
public class LocationController {

    private final LocationService locationService;

    @GetMapping("/test")
    public String test(){
        return "It is working!!";
    }

    @GetMapping("/getNearbyPlaces")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public PlacesDtoAndQueriedPlaceDto findNearbyPlaces(
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude,
            @RequestParam("radius") int radius) {
        return this.locationService.getPlaceWithNearbyPlaces(latitude,longitude,radius);
    }

    @GetMapping("/getPlaceByLocation")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public QueriedPlaceDto getPlaceByLocation(
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude) {
        return this.locationService.getPlaceByLocation(latitude,longitude);
    }


}
