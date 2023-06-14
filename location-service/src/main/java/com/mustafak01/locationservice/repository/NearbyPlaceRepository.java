package com.mustafak01.locationservice.repository;

import com.mustafak01.locationservice.model.NearbyPlace;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NearbyPlaceRepository extends MongoRepository<NearbyPlace,String> {
}
