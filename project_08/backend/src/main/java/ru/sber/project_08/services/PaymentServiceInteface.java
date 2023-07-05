package ru.sber.project_08.services;

import ru.sber.project_08.entities.PaymentInfo;

/**
 * Интерфейс для обработки оплаты
 */
public interface PaymentServiceInteface {
    boolean pay(PaymentInfo paymentInfo);
}

