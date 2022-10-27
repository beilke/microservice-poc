package com.poc.microservicepoc.apigateway.client;


import com.poc.microservicepoc.lib.customer.dto.CustomerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.poc.microservicepoc.apigateway.util.RequestPath.CUSTOMER_BASE_REF;

@FeignClient(name = "customer-service-client", url = "${customer.url}")
public interface CustomerServiceClient {

    @PostMapping(path = CUSTOMER_BASE_REF)
    CustomerDto register(@RequestBody CustomerDto request);

    @GetMapping(path = CUSTOMER_BASE_REF + "{id}")
    CustomerDto findById(@PathVariable String id);
}
