package com.poc.microservicepoc.restaurantservice.repositories;

import com.poc.microservicepoc.restaurantservice.model.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends MongoRepository<Restaurant, String> {
}
