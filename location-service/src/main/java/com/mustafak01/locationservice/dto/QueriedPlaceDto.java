package com.mustafak01.locationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueriedPlaceDto {

    private String name;
    private String placeId;
    private String address;
    private double latitude;
    private double longitude;
    private String vicinity;
    private List<String> types;
    private String url;

}
