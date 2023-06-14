package com.mustafak01.locationqueryservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PlacesDtoAndQueriedPlace {

    public QueriedPlaceDto queriedPlace;
    public List<PlaceDto> nearbyPlaces;


    public PlacesDtoAndQueriedPlace(QueriedPlaceDto queriedPlace, List<PlaceDto> nearbyPlaces) {
        this.queriedPlace = queriedPlace;
        this.nearbyPlaces = nearbyPlaces;
    }
}
