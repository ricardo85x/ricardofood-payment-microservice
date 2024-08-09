package com.ricardofood.payment.enums;

import lombok.Getter;

@Getter
public enum PaymentQueueName {
    PAYMENT_COMPLETED("payment.completed"),
    PAYMENT_FAILED("payment.failed");

    private final String name;

    PaymentQueueName(String queueName) {
        this.name = queueName;
    }
}
