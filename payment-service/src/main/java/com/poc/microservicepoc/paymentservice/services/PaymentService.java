package com.poc.microservicepoc.paymentservice.services;

import com.poc.microservicepoc.lib.customer.dto.CustomerDto;
import com.poc.microservicepoc.paymentservice.model.Payee;
import com.poc.microservicepoc.paymentservice.model.Payer;
import com.poc.microservicepoc.paymentservice.model.Payment;

public interface PaymentService {

    Payment authorizeCustomer(CustomerDto customer);

    void failCustomerAuthorization(CustomerDto customer, String reason);
}
