package com.poc.microservicepoc.customerservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Document(collection= "consumed-events")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsumedEvent implements Serializable {
    private static final long serialVersionUID = 8049100074666503841L;
    @Id
    private String id;
    private String eventId;
    private LocalDateTime timestamp;
}
