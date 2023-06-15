package com.mustafak01.locationservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
public class PlacesDtoAndQueriedPlaceDto {

    public QueriedPlaceDto queriedPlace;
    public List<PlaceDto> nearbyPlaces;


    public PlacesDtoAndQueriedPlaceDto(QueriedPlaceDto queriedPlace, List<PlaceDto> nearbyPlaces) {
        this.queriedPlace = queriedPlace;
        this.nearbyPlaces = nearbyPlaces;
    }
}
