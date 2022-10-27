package com.poc.microservicepoc.paymentservice.messaging.consumers.customer;

import com.poc.microservicepoc.lib.common.exceptions.BusinessException;
import com.poc.microservicepoc.lib.common.exceptions.Messages;
import com.poc.microservicepoc.lib.customer.dto.CustomerDto;
import com.poc.microservicepoc.lib.customer.events.CustomerPendingRegistrationEventPayload;
import com.poc.microservicepoc.paymentservice.model.ConsumedEvent;
import com.poc.microservicepoc.paymentservice.repositories.ConsumedEventRepository;
import com.poc.microservicepoc.paymentservice.services.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.function.Consumer;

import static com.poc.microservicepoc.lib.common.constants.EventConstants.CUSTOMER_PENDING_REGISTRATION_EVENT;

@Service(CUSTOMER_PENDING_REGISTRATION_EVENT)
@Slf4j
@Transactional
public class CustomerPendingRegistrationEventConsumer implements Consumer<Message<CustomerPendingRegistrationEventPayload>> {

    private final PaymentService paymentService;
    private final ConsumedEventRepository consumedEventRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerPendingRegistrationEventConsumer(PaymentService paymentService, ConsumedEventRepository consumedEventRepository, ModelMapper modelMapper) {
        this.paymentService = paymentService;
        this.consumedEventRepository = consumedEventRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void accept(Message<CustomerPendingRegistrationEventPayload> event) {
        String eventId = (String) event.getHeaders().get("eventId");
        //skip an already consumed event
        if(consumedEventRepository.findByEventId(eventId) != null){
            log.info("Event with id " + eventId + " already processed");
            return;
        }
        CustomerDto customer = event.getPayload().getCustomerDto();
        try{
            paymentService.authorizeCustomer(customer);
        }catch (BusinessException e){
            //do compensation
            log.error(e.getMessage());
            paymentService.failCustomerAuthorization(customer, e.getMessage());
        }catch(Exception e){
            log.error(e.getMessage());
            paymentService.failCustomerAuthorization(customer, Messages.TECHNICAL_ERROR);
        }
        //save the consumed event
        consumedEventRepository.save(ConsumedEvent.builder().eventId(eventId).timestamp(LocalDateTime.now()).build());
    }
}
