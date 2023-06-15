package com.mustafak01.locationqueryservice.service;

import com.google.maps.*;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;
import com.mustafak01.locationqueryservice.dto.PlaceDto;
import com.mustafak01.locationqueryservice.dto.PlacesDtoAndQueriedPlace;
import com.mustafak01.locationqueryservice.dto.QueriedPlaceDto;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoogleMapsService {

    private static final String API_KEY = "Put your api key here";

    private final GeoApiContext context;

    public GoogleMapsService() {
        this.context = new GeoApiContext.Builder().apiKey(API_KEY).build();
    }

    public PlacesDtoAndQueriedPlace getPlacesAndQueriedPlace(double latitude, double longitude, int radius) throws IOException, InterruptedException, ApiException {
        QueriedPlaceDto queriedPlace = getQueriedPlace(latitude, longitude);
        List<PlaceDto> nearbyPlaces = getNearByPlaces(latitude, longitude, radius);
        List<PlaceDto> places = new ArrayList<>();
        for (PlaceDto place:nearbyPlaces) {
            double distance = calcDistance(place.getLatitude(), place.getLongitude(), latitude , longitude);
            if (distance<=radius) places.add(place);
        }
        return new PlacesDtoAndQueriedPlace(queriedPlace, places);
    }

    public List<PlaceDto> getNearByPlaces(double latitude, double longitude, int radius) throws IOException, InterruptedException, ApiException {
        return this.convertPlacesToDto(this.findNearbyPlaces(latitude,longitude,radius));
    }

    public List<PlacesSearchResult> findNearbyPlaces(double latitude, double longitude, int radius)
            throws ApiException, InterruptedException, IOException {
        LatLng location = new LatLng(latitude, longitude);
        NearbySearchRequest request = PlacesApi.nearbySearchQuery(context, location).radius(radius);
        PlacesSearchResponse placesSearchResponse = request.await();
        List<PlacesSearchResult> results = new ArrayList<>(Arrays.asList(placesSearchResponse.results));
        String nextPageToken = placesSearchResponse.nextPageToken;

        while (nextPageToken!=null) {
            Thread.sleep(2000);
            request=PlacesApi.nearbySearchQuery(context, location).radius(radius).pageToken(nextPageToken);
            placesSearchResponse=request.await();
            results.addAll(Arrays.asList(placesSearchResponse.results));
            nextPageToken=placesSearchResponse.nextPageToken;
        }

        return results;
    }

    public PlaceDetails findPlaceByCoordinates(double latitude, double longitude) throws ApiException, InterruptedException, IOException {
        LatLng location = new LatLng(latitude, longitude);
        GeocodingApiRequest geocodingReq = GeocodingApi.reverseGeocode(context, location);
        GeocodingResult[] geocodingResults = geocodingReq.await();
        String placeId = geocodingResults[0].placeId;
        PlaceDetailsRequest placeDetailsReq = PlacesApi.placeDetails(context, placeId);
        return placeDetailsReq.await();
    }


    private List<PlaceDto> convertPlacesToDto(List<PlacesSearchResult> results) {
        return results.stream()
                .map(result -> PlaceDto.builder()
                        .placeId(result.placeId)
                        .name(result.name)
                        .latitude(result.geometry.location.lat)
                        .longitude(result.geometry.location.lng)
                        .rating(result.rating)
                        .vicinity(result.vicinity)
                        .types(result.types)
                        .build())
                .collect(Collectors.toList());
    }

    public QueriedPlaceDto getQueriedPlace(double latitude, double longitude) throws ApiException, InterruptedException, IOException {
        PlaceDetails placeDetails = findPlaceByCoordinates(latitude, longitude);
        return QueriedPlaceDto.builder()
                .name(placeDetails.name)
                .placeId(placeDetails.placeId)
                .address(placeDetails.formattedAddress)
                .latitude(placeDetails.geometry.location.lat)
                .longitude(placeDetails.geometry.location.lng)
                .vicinity(placeDetails.vicinity)
                .types(List.of(Arrays.toString(placeDetails.types)))
                .url(String.valueOf(placeDetails.url))
                .build();
    }

    public double calcDistance(double lat1, double lon1, double lat2, double lon2) {
        double earthRad = 6371;
        double dLatitude = Math.toRadians(lat2 - lat1);
        double dLongitude = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLatitude / 2) * Math.sin(dLatitude / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLongitude / 2) * Math.sin(dLongitude / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRad * c * 1000;
    }

}
