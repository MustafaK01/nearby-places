package com.mustafak01.locationservice.repository;

import com.mustafak01.locationservice.model.QueriedPlace;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QueriedPlaceRepository extends MongoRepository<QueriedPlace,String> {
}
