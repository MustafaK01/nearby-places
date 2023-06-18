package com.mustafak01.locationservice.dto;

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
public class PlacesDtoAndQueriedPlaceDto {

    @Nullable
    public QueriedPlaceDto queriedPlace;
    @Nullable
    public List<PlaceDto> nearbyPlaces;
    @Nullable
    public String message;

    public PlacesDtoAndQueriedPlaceDto(QueriedPlaceDto queriedPlace, List<PlaceDto> nearbyPlaces) {
        this.queriedPlace = queriedPlace;
        this.nearbyPlaces = nearbyPlaces;
    }
    public PlacesDtoAndQueriedPlaceDto(String message){
        this.message = message;
    }
}
