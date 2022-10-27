package com.poc.microservicepoc.orderservice.services;

import com.poc.microservicepoc.orderservice.model.Order;

import java.util.List;

public interface OrderService {
    String placeOrder(Order order);
    void startOrderDelivery(Order order);
    void finishOrderDelivery(Order order);
    String checkOrderStatus(String orderId);
    List<Order> checkConsumerOrders(String consumerId);

}
