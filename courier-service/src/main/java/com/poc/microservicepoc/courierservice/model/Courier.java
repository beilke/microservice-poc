package com.poc.microservicepoc.courierservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "couriers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Courier {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String bankAccount;
    private String bankName;
}
