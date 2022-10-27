package com.poc.microservicepoc.lib.customer.events;

import com.poc.microservicepoc.lib.common.events.DomainEvent;
import com.poc.microservicepoc.lib.customer.dto.CustomerDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("customer-events")
@SuperBuilder
@Data
@NoArgsConstructor
public abstract class CustomerEvent extends DomainEvent {
    private static final long serialVersionUID = -2468469840737299150L;
}
