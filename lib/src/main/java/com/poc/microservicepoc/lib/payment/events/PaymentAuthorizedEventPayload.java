package com.poc.microservicepoc.lib.payment.events;

import com.poc.microservicepoc.lib.payment.dto.PaymentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentAuthorizedEventPayload implements Serializable {
    private static final long serialVersionUID = 8931395645044774170L;
    private PaymentDto payment;
    private String customerId;
}
