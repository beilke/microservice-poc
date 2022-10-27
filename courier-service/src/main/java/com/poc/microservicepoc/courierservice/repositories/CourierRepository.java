package com.poc.microservicepoc.courierservice.repositories;

import com.poc.microservicepoc.courierservice.model.Courier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourierRepository extends MongoRepository<Courier, String> {
}
