package com.poc.microservicepoc.customerservice.services;

import com.poc.microservicepoc.customerservice.model.Customer;
import com.poc.microservicepoc.lib.customer.dto.CustomerDto;

public interface CustomerService {
    Customer createPendingCustomerRegistration(Customer customer);

    void approveRegistration(String customerId);

    void rejectRegistration(String customerId, String reason);

    Customer findById(String id);
}
