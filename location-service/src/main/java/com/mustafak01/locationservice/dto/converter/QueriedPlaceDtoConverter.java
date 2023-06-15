package com.mustafak01.locationservice.dto.converter;

import com.mustafak01.locationservice.dto.QueriedPlaceDto;
import com.mustafak01.locationservice.model.QueriedPlace;
import org.springframework.stereotype.Component;

@Component
public class QueriedPlaceDtoConverter {

    public QueriedPlace convertToEntity(QueriedPlaceDto queriedPlaceDto){
        return QueriedPlace.builder()
                .queriedPlaceId(queriedPlaceDto.getPlaceId())
                .name(queriedPlaceDto.getName())
                .address(queriedPlaceDto.getAddress())
                .latitude(queriedPlaceDto.getLatitude())
                .longitude(queriedPlaceDto.getLongitude())
                .vicinity(queriedPlaceDto.getVicinity())
                .url(queriedPlaceDto.getUrl())
                .types(queriedPlaceDto.getTypes())
                .build();
    }

    public QueriedPlaceDto convertToDto(QueriedPlace queriedPlace){
        return QueriedPlaceDto.builder()
                .placeId(queriedPlace.getQueriedPlaceId())
                .name(queriedPlace.getName())
                .address(queriedPlace.getAddress())
                .latitude(queriedPlace.getLatitude())
                .longitude(queriedPlace.getLongitude())
                .vicinity(queriedPlace.getVicinity())
                .url(queriedPlace.getUrl())
                .types(queriedPlace.getTypes())
                .build();
    }

}
