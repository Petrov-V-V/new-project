package ru.sber.project_06.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Класс для хранения данных об оплате
 */
@Data
@AllArgsConstructor
public class PaymentInfo {
    private String cardNumber;
    long userId;
}