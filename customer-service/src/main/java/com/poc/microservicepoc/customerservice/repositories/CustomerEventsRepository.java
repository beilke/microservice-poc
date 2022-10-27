package com.poc.microservicepoc.customerservice.repositories;

import com.poc.microservicepoc.lib.customer.events.CustomerEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerEventsRepository extends MongoRepository<CustomerEvent, UUID> {
}
