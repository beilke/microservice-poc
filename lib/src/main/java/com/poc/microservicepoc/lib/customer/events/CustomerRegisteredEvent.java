package com.poc.microservicepoc.lib.customer.events;

import com.poc.microservicepoc.lib.customer.dto.CustomerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRegisteredEvent extends CustomerEvent{
    private static final long serialVersionUID = 2046835779768635862L;
    private CustomerRegisteredEventPayload payload;
}
