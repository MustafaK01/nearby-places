package com.mustafak01.locationservice.dto.converter;

import com.mustafak01.locationservice.dto.PlaceDto;
import com.mustafak01.locationservice.model.NearbyPlace;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlaceDtoConverter {

    public NearbyPlace convertToEntity(PlaceDto placeDto){
        return NearbyPlace.builder()
                .nearbyPlaceId(placeDto.getPlaceId())
                .name(placeDto.getName())
                .latitude(placeDto.getLatitude())
                .longitude(placeDto.getLongitude())
                .vicinity(placeDto.getVicinity())
                .types(List.of(placeDto.getTypes()))
                .rating(placeDto.getRating())
                .build();
    }

    public List<NearbyPlace> convertToEntityList(List<PlaceDto> placeDto){
        return placeDto.stream().map(dto->NearbyPlace.builder()
                .nearbyPlaceId(dto.getPlaceId())
                .name(dto.getName())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .vicinity(dto.getVicinity())
                .types(List.of(dto.getTypes()))
                .rating(dto.getRating())
                .build()).collect(Collectors.toList());
    }

    public List<PlaceDto> convertToDtoList(List<NearbyPlace> nearbyPlaces){
        return nearbyPlaces.stream().map(nearbyPlace->PlaceDto.builder()
                .placeId(nearbyPlace.getNearbyPlaceId())
                .name(nearbyPlace.getName())
                .latitude(nearbyPlace.getLatitude())
                .longitude(nearbyPlace.getLongitude())
                .vicinity(nearbyPlace.getVicinity())
                .types(nearbyPlace.getTypes().toArray(String[]::new))
                .rating(nearbyPlace.getRating())
                .build()).collect(Collectors.toList());
    }


}
