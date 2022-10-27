package com.poc.microservicepoc.customerservice.messaging.consumers.payment;

import com.poc.microservicepoc.customerservice.model.ConsumedEvent;
import com.poc.microservicepoc.customerservice.repositories.ConsumedEventRepository;
import com.poc.microservicepoc.customerservice.services.CustomerService;
import com.poc.microservicepoc.lib.payment.events.PaymentAuthorizationFailedEventPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.function.Consumer;

import static com.poc.microservicepoc.lib.common.constants.EventConstants.PAYMENT_NOT_AUTHORIZED_EVENT;

@Service(PAYMENT_NOT_AUTHORIZED_EVENT)
@Slf4j
@Transactional
public class PaymentNotAuthorizedEventConsumer implements Consumer<Message<PaymentAuthorizationFailedEventPayload>> {

    private final CustomerService customerService;
    private final ConsumedEventRepository consumedEventRepository;

    @Autowired
    public PaymentNotAuthorizedEventConsumer(CustomerService customerService, ConsumedEventRepository consumedEventRepository) {
        this.customerService = customerService;
        this.consumedEventRepository = consumedEventRepository;
    }

    @Override
    public void accept(Message<PaymentAuthorizationFailedEventPayload> event) {
        String eventId = (String) event.getHeaders().get("eventId");
        //skip an already consumed event
        if(consumedEventRepository.findByEventId(eventId) != null){
            log.info("Event with id " + eventId + " already processed");
            return;
        }
        customerService.rejectRegistration(event.getPayload().getCustomerId(), event.getPayload().getReason());
        //save the consumed event
        consumedEventRepository.save(ConsumedEvent.builder().eventId(eventId).timestamp(LocalDateTime.now()).build());
    }
}
