package com.poc.microservicepoc.lib.payment.events;

import com.poc.microservicepoc.lib.common.events.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("payment-events")
@SuperBuilder
@Data
@NoArgsConstructor
public class PaymentEvent extends DomainEvent {
    private static final long serialVersionUID = 4715100218775419326L;
}
