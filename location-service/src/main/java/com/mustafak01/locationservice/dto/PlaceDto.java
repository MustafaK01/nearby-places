package com.mustafak01.locationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDto {

    private String placeId;
    private String name;
    private double latitude;
    private double longitude;
    private double rating;
    private String vicinity;
    private String[] types;

}

