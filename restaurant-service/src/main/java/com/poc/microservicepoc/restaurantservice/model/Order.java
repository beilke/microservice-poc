package com.poc.microservicepoc.restaurantservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection= "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    private String id;
    private String consumerId;
    private String courierId;
    private List<MenuItem> items;
    private Address deliveryAddress;
    private LocalDateTime deliveryTime;
    private Double total;
    private OrderStatus status;
}
