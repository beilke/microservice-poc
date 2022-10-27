package com.poc.microservicepoc.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
@Document(collection= "couriers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    private String id;
    private String consumerId;
    private String courierId;
    private List<OrderItem> items;
    private Address deliveryAddress;
    private LocalDateTime deliveryTime;
    private Double total;
    private OrderStatus status;
}
