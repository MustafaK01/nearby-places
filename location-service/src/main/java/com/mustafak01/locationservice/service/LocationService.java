package com.mustafak01.locationservice.service;

import com.mustafak01.locationservice.dto.QueriedPlaceDto;
import com.mustafak01.locationservice.exception.CouldNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Transactional
public class LocationService {

    private final WebClient.Builder webClientBuilder;

    public QueriedPlaceDto getPlaceByLocation(double latitude , double longitude){
        return this.getQueriedPlace(latitude,longitude);
    }


    private QueriedPlaceDto getQueriedPlace(double latitude , double longitude){
        QueriedPlaceDto queriedPlaceResponse = webClientBuilder.build().get().uri("http://location-query-service/api/location-query-service/getQueriedPlaceData"
                        ,uriBuilder -> uriBuilder.queryParam("latitude",latitude)
                                .queryParam("longitude",longitude)
                                .build())
                .retrieve().bodyToMono(QueriedPlaceDto.class)
                .block();
        if(queriedPlaceResponse!=null) return queriedPlaceResponse;
        else throw new CouldNotFoundException();
    }


}
