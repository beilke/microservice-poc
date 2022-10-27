package com.poc.microservicepoc.customerservice.repositories;

import com.poc.microservicepoc.customerservice.model.ConsumedEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumedEventRepository extends MongoRepository<ConsumedEvent, String> {

    ConsumedEvent findByEventId(String eventId);
}
