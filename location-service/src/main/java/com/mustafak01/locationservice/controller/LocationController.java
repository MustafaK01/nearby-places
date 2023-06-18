package com.mustafak01.locationservice.controller;

import com.mustafak01.locationservice.dto.PlacesDtoAndQueriedPlaceDto;
import com.mustafak01.locationservice.dto.QueriedPlaceDto;
import com.mustafak01.locationservice.service.LocationService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/location-service")
@RequiredArgsConstructor
@CrossOrigin
public class LocationController {

    private final LocationService locationService;

    @GetMapping("/getNearbyPlaces")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @CircuitBreaker(name = "queryLocation",fallbackMethod = "fallbackMethod")
    @TimeLimiter(name="queryLocation")
    @Retry(name="queryLocation")
    public CompletableFuture<PlacesDtoAndQueriedPlaceDto> findNearbyPlaces(
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude,
            @RequestParam("radius") int radius) {
        return CompletableFuture.supplyAsync(()->this.locationService.getPlaceWithNearbyPlaces(latitude,longitude,radius));
    }

    @GetMapping("/getPlaceByLocation")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public QueriedPlaceDto getPlaceByLocation(
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude) {
        return this.locationService.getPlaceByLocation(latitude,longitude);
    }

    public CompletableFuture<PlacesDtoAndQueriedPlaceDto> fallbackMethod(
            double latitude,
            double longitude,
            int radius,RuntimeException runtimeException){
        PlacesDtoAndQueriedPlaceDto placesDtoAndQueriedPlaceDto = new PlacesDtoAndQueriedPlaceDto();
        placesDtoAndQueriedPlaceDto.setMessage("Oops! Something went wrong, please try again");
        return CompletableFuture.supplyAsync(()->placesDtoAndQueriedPlaceDto);
    }


}
