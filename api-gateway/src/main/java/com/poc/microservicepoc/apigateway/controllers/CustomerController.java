package com.poc.microservicepoc.apigateway.controllers;

import com.poc.microservicepoc.apigateway.client.CustomerServiceClient;
import com.poc.microservicepoc.lib.customer.dto.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@Slf4j
@RequestMapping(path = "/customers/")
public class CustomerController {

    private final CustomerServiceClient customerServiceClient;

    @Autowired
    public CustomerController(CustomerServiceClient customerServiceClient) {
        this.customerServiceClient = customerServiceClient;
    }


    @PostMapping
    public ResponseEntity<CustomerDto> register(@RequestBody CustomerDto customer) {
        customer = customerServiceClient.register(customer);
        return new ResponseEntity<>(customer, OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CustomerDto> findById(@PathVariable("id") String id) {
        CustomerDto customer = customerServiceClient.findById(id);
        return new ResponseEntity<>(customer, OK);

    }

}
