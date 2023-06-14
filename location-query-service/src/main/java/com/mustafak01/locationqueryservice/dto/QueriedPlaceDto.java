package com.mustafak01.locationqueryservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
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
