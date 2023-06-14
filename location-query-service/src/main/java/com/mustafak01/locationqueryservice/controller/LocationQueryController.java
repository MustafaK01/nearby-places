package com.mustafak01.locationqueryservice.controller;

import com.google.maps.errors.ApiException;
import com.mustafak01.locationqueryservice.dto.PlacesDtoAndQueriedPlace;
import com.mustafak01.locationqueryservice.service.GoogleMapsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/location-query-service")
@RequiredArgsConstructor
public class LocationQueryController {

    private final GoogleMapsService googleMapsService;

    //radius is calculating in meters, just for now.
    @GetMapping("/findNearbyPlaces")
    public PlacesDtoAndQueriedPlace findNearbyPlaces(
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude,
            @RequestParam("radius") int radius) throws IOException, InterruptedException, ApiException {
        return this.googleMapsService.getPlacesAndQueriedPlace(latitude,longitude,radius);
    }

}
