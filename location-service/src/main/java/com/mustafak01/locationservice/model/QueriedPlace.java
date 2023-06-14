package com.mustafak01.locationservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(value="queriedPlace")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class QueriedPlace {

    @Id
    private String queriedPlaceId;
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private String vicinity;
    private String url;
    private List<String> types;

}
