package com.poc.microservicepoc.paymentservice.repositories;

import com.poc.microservicepoc.paymentservice.model.ConsumedEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumedEventRepository extends MongoRepository<ConsumedEvent, String> {

    ConsumedEvent findByEventId(String eventId);
}