package com.mustafak01.locationservice.service;

import com.mustafak01.locationservice.dto.PlaceDto;
import com.mustafak01.locationservice.dto.PlacesDtoAndQueriedPlaceDto;
import com.mustafak01.locationservice.dto.QueriedPlaceDto;
import com.mustafak01.locationservice.dto.converter.PlaceDtoConverter;
import com.mustafak01.locationservice.dto.converter.QueriedPlaceDtoConverter;
import com.mustafak01.locationservice.exception.CouldNotFoundException;
import com.mustafak01.locationservice.model.Distance;
import com.mustafak01.locationservice.model.NearbyPlace;
import com.mustafak01.locationservice.model.QueriedPlace;
import com.mustafak01.locationservice.repository.DistanceRepository;
import com.mustafak01.locationservice.repository.NearbyPlaceRepository;
import com.mustafak01.locationservice.repository.QueriedPlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LocationService {

    private final WebClient.Builder webClientBuilder;
    private final QueriedPlaceRepository queriedPlaceRepository;
    private final NearbyPlaceRepository nearbyPlaceRepository;
    private final DistanceRepository distanceRepository;
    private final PlaceDtoConverter placeDtoConverter;
    private final QueriedPlaceDtoConverter queriedPlaceDtoConverter;

    public QueriedPlaceDto getPlaceByLocation(double latitude , double longitude){
        return this.getQueriedPlace(latitude,longitude);
    }

    // TODO: 15.06.2023
    // Simplify the functions
    // validate returning data

    public PlacesDtoAndQueriedPlaceDto getPlaceWithNearbyPlaces(double latitude , double longitude, int radius){
        PlacesDtoAndQueriedPlaceDto placesAndNearByPlaces = this.getPlaceAndNearbyPlaces(latitude, longitude, radius);
        QueriedPlaceDto queriedPlaceDto=placesAndNearByPlaces.getQueriedPlace();
        List<PlaceDto> nearByPlaces = placesAndNearByPlaces.getNearbyPlaces();
        Optional<QueriedPlace> queriedPlace = this.queriedPlaceRepository.findById(queriedPlaceDto.getPlaceId());
        if(queriedPlaceDto==null||queriedPlaceDto.getPlaceId()==null) throw new CouldNotFoundException();
        if (queriedPlace.isEmpty()){
            this.queriedPlaceRepository.save(this.queriedPlaceDtoConverter.convertToEntity(queriedPlaceDto));
            this.nearbyPlaceRepository.saveAll(this.placeDtoConverter.convertToEntityList(nearByPlaces));
            this.distanceRepository.saveAll(this.getDistances(
                    this.queriedPlaceDtoConverter.convertToEntity(queriedPlaceDto),
                    this.placeDtoConverter.convertToEntityList(nearByPlaces)));
            return placesAndNearByPlaces;
        }
        this.saveNewPlaces(queriedPlaceDto,nearByPlaces);
        return this.resultForNewPlaces(queriedPlace.get());
    }

    private void saveNewPlaces(QueriedPlaceDto queriedPlaceDto, List<PlaceDto> placeDtoList){
        QueriedPlace queriedPlace = this.queriedPlaceDtoConverter.convertToEntity(queriedPlaceDto);
        List<NearbyPlace> nearbyPlaces = this.placeDtoConverter.convertToEntityList(placeDtoList);
        List<NearbyPlace> filteredNearbyPlaces = new ArrayList<>();
        if(nearbyPlaces.size()==0 || queriedPlace==null) return;
        double furthestPlace = this.getFurthestPlaceInDistances(queriedPlace);
        for (NearbyPlace nearByPlace: nearbyPlaces) {
            double distanceBetweenPlaces = this.calcDistance(
                    queriedPlace.getLatitude(),queriedPlace.getLongitude()
                    ,nearByPlace.getLatitude(),nearByPlace.getLongitude());
            if(distanceBetweenPlaces>furthestPlace){
                filteredNearbyPlaces.add(nearByPlace);
            }
        }
        if (filteredNearbyPlaces.size()==0) return;
        this.nearbyPlaceRepository.saveAll(filteredNearbyPlaces);
        this.distanceRepository.saveAll(this.getDistances(queriedPlace,filteredNearbyPlaces));
    }

    private PlacesDtoAndQueriedPlaceDto resultForNewPlaces(QueriedPlace queriedPlace){
        List<Distance> distances = this.distanceRepository.findAllByQueriedPlace(queriedPlace);
        List<NearbyPlace> nearbyPlaces = new ArrayList<>();
        for (Distance distance:distances) {
            nearbyPlaces.add(distance.getNearbyPlace());
        }
        return new PlacesDtoAndQueriedPlaceDto(this.queriedPlaceDtoConverter.convertToDto(queriedPlace)
        ,this.placeDtoConverter.convertToDtoList(nearbyPlaces));
    }

    private List<Distance> getDistances(QueriedPlace queriedPlace, List<NearbyPlace> nearbyPlaces){
        List<Distance> distanceList = new ArrayList<>();
        for (NearbyPlace nearByPlace: nearbyPlaces) {
            double distanceBetweenPlaces = this.calcDistance(
                    queriedPlace.getLatitude(),queriedPlace.getLongitude()
                    ,nearByPlace.getLatitude(),nearByPlace.getLongitude());
            distanceList.add(Distance.builder()
                    .queriedPlace(queriedPlace)
                    .nearbyPlace(nearByPlace)
                    .distance(distanceBetweenPlaces)
                    .build());
        }
        return distanceList;
    }

    //It will use in order to calculate distance between queried place and nearby places
    public double calcDistance(double lat1, double lon1, double lat2, double lon2) {
        double earthRad = 6371; //earth radius
        //lat and lng differences radians
        double dLatitude = Math.toRadians(lat2 - lat1);
        double dLongitude = Math.toRadians(lon2 - lon1);

        //haversine
        double a = Math.sin(dLatitude / 2) * Math.sin(dLatitude / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLongitude / 2) * Math.sin(dLongitude / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return earthRad * c * 1000; // convert to meter
    }

    public double getFurthestPlaceInDistances(QueriedPlace queriedPlace){
        List<Distance> distances = this.distanceRepository.findAllByQueriedPlace(queriedPlace);
        double furthest = Double.MIN_VALUE;
        for (Distance distance: distances) {
            if (distance.getDistance() > furthest) {
                furthest = distance.getDistance();
            }
        }
        return furthest;
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

    private PlacesDtoAndQueriedPlaceDto getPlaceAndNearbyPlaces(double latitude , double longitude, int radius){
        PlacesDtoAndQueriedPlaceDto nearbyPlacesAndQueriedPlace = webClientBuilder.build().get().uri("http://location-query-service/api/location-query-service/findNearbyPlaces"
                        ,uriBuilder -> uriBuilder.queryParam("latitude",latitude)
                                .queryParam("longitude",longitude)
                                .queryParam("radius",radius)
                                .build())
                .retrieve().bodyToMono(PlacesDtoAndQueriedPlaceDto.class)
                .block();
        if(nearbyPlacesAndQueriedPlace!=null) return nearbyPlacesAndQueriedPlace;
        else throw new CouldNotFoundException();
    }

}
