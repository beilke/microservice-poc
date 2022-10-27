package com.poc.microservicepoc.restaurantservice.services;

import com.poc.microservicepoc.restaurantservice.model.Menu;
import com.poc.microservicepoc.restaurantservice.model.Order;
import com.poc.microservicepoc.restaurantservice.model.Restaurant;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Override
    public List<Restaurant> checkRestaurants(String geolocation) {
        return null;
    }

    @Override
    public Menu checkRestaurantMenu(String restaurantId) {
        return null;
    }

    @Override
    public void acceptOrder(Order order) {

    }
}
