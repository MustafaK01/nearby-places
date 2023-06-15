package com.mustafak01.locationservice.repository;

import com.mustafak01.locationservice.model.Distance;
import com.mustafak01.locationservice.model.QueriedPlace;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DistanceRepository extends MongoRepository<Distance,String> {

    List<Distance> findAllByQueriedPlace(QueriedPlace queriedPlace);

}
