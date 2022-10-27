package com.poc.microservicepoc.orderservice.services;

import com.poc.microservicepoc.orderservice.model.Order;
import com.poc.microservicepoc.orderservice.model.OrderStatus;
import com.poc.microservicepoc.orderservice.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public String placeOrder(Order order) {
        order.setStatus(OrderStatus.PENDING);
        return orderRepository.save(order).getId();
    }

    @Override
    public void startOrderDelivery(Order order) {

    }

    @Override
    public void finishOrderDelivery(Order order) {

    }

    @Override
    public String checkOrderStatus(String orderId) {
        return null;
    }

    @Override
    public List<Order> checkConsumerOrders(String consumerId) {
        return null;
    }
}
