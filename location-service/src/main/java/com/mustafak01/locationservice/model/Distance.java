package com.mustafak01.locationservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(value="distance")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Distance {

    @Id
    private String distancesId;

    @DBRef
    private QueriedPlace queriedPlace;

    @DBRef
    private NearbyPlace nearbyPlace;

    private double distance;

}
