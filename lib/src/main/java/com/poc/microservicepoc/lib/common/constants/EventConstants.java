package com.poc.microservicepoc.lib.common.constants;

public interface EventConstants {
    String CUSTOMER_PENDING_REGISTRATION_EVENT = "CustomerPendingRegistrationEvent";
    String CUSTOMER_REGISTERED_EVENT = "CustomerRegisteredEvent";
    String CUSTOMER_NOT_REGISTERED_EVENT = "CustomerNotRegisteredEvent";
    String PAYMENT_AUTHORIZED_EVENT = "PaymentAuthorizedEvent";
    String PAYMENT_NOT_AUTHORIZED_EVENT = "PaymentNotAuthorizedEvent";
}
