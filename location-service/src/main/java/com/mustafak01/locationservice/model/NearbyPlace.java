package com.mustafak01.locationservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(value="nearbyPlace")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class NearbyPlace {

    @Id
    private String nearbyPlaceId;
    private String name;
    private double latitude;
    private double longitude;
    private double rating;
    private String vicinity;
    private List<String> types;

}
