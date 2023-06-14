package com.mustafak01.locationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
public class PlacesDtoAndQueriedPlace {

    public QueriedPlaceDto queriedPlace;
    public List<PlaceDto> nearbyPlaces;


    public PlacesDtoAndQueriedPlace(QueriedPlaceDto queriedPlace, List<PlaceDto> nearbyPlaces) {
        this.queriedPlace = queriedPlace;
        this.nearbyPlaces = nearbyPlaces;
    }
}
