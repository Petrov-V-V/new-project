package ru.sber.project_06.services;

import ru.sber.project_06.entities.PaymentInfo;

/**
 * Интерфейс для обработки оплаты
 */
public interface PaymentServiceInteface {
    boolean pay(PaymentInfo paymentInfo);
}

