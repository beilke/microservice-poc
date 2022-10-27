package com.poc.microservicepoc.lib.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayeeDto {
    private String name;
    private String bankAccount;
    private String bankName;
}
