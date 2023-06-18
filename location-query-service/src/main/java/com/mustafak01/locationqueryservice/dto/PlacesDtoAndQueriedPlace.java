package com.mustafak01.locationqueryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlacesDtoAndQueriedPlace {

    @Nullable
    public String message;
    @Nullable
    public QueriedPlaceDto queriedPlace;
    @Nullable
    public List<PlaceDto> nearbyPlaces;


    public PlacesDtoAndQueriedPlace(QueriedPlaceDto queriedPlace, List<PlaceDto> nearbyPlaces) {
        this.queriedPlace = queriedPlace;
        this.nearbyPlaces = nearbyPlaces;
    }

    public PlacesDtoAndQueriedPlace(String message){
        this.message = message;
    }
}
