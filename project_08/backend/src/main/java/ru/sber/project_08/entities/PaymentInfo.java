package ru.sber.project_08.entities;

import org.springframework.web.bind.annotation.CrossOrigin;

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