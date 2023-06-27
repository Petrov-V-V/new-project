package ru.sber.project_06.services;

import ru.sber.project_06.entities.PaymentInfo;

public interface PaymentServiceInteface {
    boolean pay(PaymentInfo paymentInfo);
}

