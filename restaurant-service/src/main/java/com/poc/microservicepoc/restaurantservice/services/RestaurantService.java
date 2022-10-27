package com.poc.microservicepoc.restaurantservice.services;

import com.poc.microservicepoc.restaurantservice.model.Menu;
import com.poc.microservicepoc.restaurantservice.model.Order;
import com.poc.microservicepoc.restaurantservice.model.Restaurant;

import java.util.List;

public interface RestaurantService {

    List<Restaurant> checkRestaurants(String geolocation);
    Menu checkRestaurantMenu(String restaurantId);
    void acceptOrder(Order order);
}
