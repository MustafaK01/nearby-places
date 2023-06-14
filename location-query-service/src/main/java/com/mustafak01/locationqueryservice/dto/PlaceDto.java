package com.mustafak01.locationqueryservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlaceDto {

    private String placeId;
    private String name;
    private double latitude;
    private double longitude;
    private double rating;
    private String vicinity;
    private String[] types;

}

