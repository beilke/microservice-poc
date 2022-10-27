package com.poc.microservicepoc.paymentservice.repositories;

import com.poc.microservicepoc.lib.payment.events.PaymentEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentEventsRepository extends MongoRepository<PaymentEvent, String> {
}
