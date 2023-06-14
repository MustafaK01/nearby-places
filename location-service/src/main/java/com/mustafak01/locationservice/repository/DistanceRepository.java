package com.mustafak01.locationservice.repository;

import com.mustafak01.locationservice.model.Distance;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DistanceRepository extends MongoRepository<Distance,String> {
}
