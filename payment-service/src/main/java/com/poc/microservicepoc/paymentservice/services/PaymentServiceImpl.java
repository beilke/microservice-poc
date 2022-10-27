package com.poc.microservicepoc.paymentservice.services;

import com.poc.microservicepoc.lib.common.annotations.CompensationFor;
import com.poc.microservicepoc.lib.common.annotations.Pivot;
import com.poc.microservicepoc.lib.common.exceptions.BusinessException;
import com.poc.microservicepoc.lib.common.exceptions.Messages;
import com.poc.microservicepoc.lib.customer.dto.CustomerDto;
import com.poc.microservicepoc.lib.payment.dto.PaymentDto;
import com.poc.microservicepoc.lib.payment.events.PaymentAuthorizationFailedEvent;
import com.poc.microservicepoc.lib.payment.events.PaymentAuthorizationFailedEventPayload;
import com.poc.microservicepoc.lib.payment.events.PaymentAuthorizedEvent;
import com.poc.microservicepoc.lib.payment.events.PaymentAuthorizedEventPayload;
import com.poc.microservicepoc.paymentservice.model.Card;
import com.poc.microservicepoc.paymentservice.model.Payee;
import com.poc.microservicepoc.paymentservice.model.Payer;
import com.poc.microservicepoc.paymentservice.model.Payment;
import com.poc.microservicepoc.paymentservice.repositories.PaymentEventsRepository;
import com.poc.microservicepoc.paymentservice.repositories.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.poc.microservicepoc.lib.common.constants.AggregateType.PAYMENT;
import static com.poc.microservicepoc.lib.common.constants.EventConstants.*;
import static com.poc.microservicepoc.lib.common.constants.SagaConstants.CUSTOMER_REGISTRATION_SAGA;

@Service
@Transactional
@Slf4j
public class PaymentServiceImpl implements PaymentService{

    private final PaymentRepository paymentRepository;
    private final PaymentEventsRepository paymentEventsRepository;
    private final ModelMapper modelMapper;

    @Value("${authorization.fee}")
    private Double authorizationFee;
    @Value("${company.name}")
    private String companyName;
    @Value("${company.bankAccount}")
    private String companyBankAccount;
    @Value("${company.bankName}")
    private String companyBankName;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentEventsRepository paymentEventsRepository,ModelMapper modelMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentEventsRepository = paymentEventsRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Pivot(sagas = {CUSTOMER_REGISTRATION_SAGA})
    public Payment authorizeCustomer(CustomerDto customer) {
        Payment payment = buildPaymentFromCustomer(customer, true);
        if(payment.getPayer().getCard().getNumber().length() < 10){
            throw new BusinessException(Messages.CREDIT_CARD_NUMBER_WRONG);
        }
        payment = paymentRepository.save(payment);
        PaymentAuthorizedEventPayload payload = PaymentAuthorizedEventPayload.builder().customerId(customer.getId()).payment(modelMapper.map(payment, PaymentDto.class)).build();
        //save the new event to be sent
        PaymentAuthorizedEvent paymentAuthorizedEvent = PaymentAuthorizedEvent.builder().payload(payload).type(PAYMENT_AUTHORIZED_EVENT)
                .aggregateId(payment.getId()).aggregateType(PAYMENT).build();
        paymentEventsRepository.save(paymentAuthorizedEvent);
        log.info("Customer payment authorized");
        return payment;
    }

    @Override
    @CompensationFor(sagas = {CUSTOMER_REGISTRATION_SAGA}, compensatableEvents = {CUSTOMER_PENDING_REGISTRATION_EVENT})
    public void failCustomerAuthorization(CustomerDto customer, String reason) {
        Payment payment = buildPaymentFromCustomer(customer, false);
        payment = paymentRepository.save(payment);
        PaymentAuthorizationFailedEvent paymentAuthorizationFailedEvent = PaymentAuthorizationFailedEvent.builder()
                .payload(PaymentAuthorizationFailedEventPayload.builder().customerId(customer.getId()).reason(reason).payment(modelMapper.map(payment,PaymentDto.class)).build())
                .type(PAYMENT_NOT_AUTHORIZED_EVENT).aggregateId(payment.getId()).aggregateType(PAYMENT).build();
        paymentEventsRepository.save(paymentAuthorizationFailedEvent);
        log.info("Customer payment not authorized");
    }


    private Payment buildPaymentFromCustomer(CustomerDto customer, boolean authorized){
        String name = new StringBuilder(customer.getFirstName()).append(" ").append(customer.getLastName()).toString();
        Card card = modelMapper.map(customer.getCard(), Card.class);
        Payer payer = Payer.builder().name(name).card(card).build();
        Payee company = Payee.builder().name(companyName).bankAccount(companyBankAccount).bankName(companyBankName).build();
        Payment payment = Payment.builder().payer(payer).payee(company).amount(authorizationFee).authorized(authorized).build();
        return payment;
    }
}
