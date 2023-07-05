package ru.sber.project_08.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Класс хранящий информацию о счёте в банке
 */
@Data
@AllArgsConstructor
public class BankAccount {
    private String cardNumber;
    private BigDecimal sum;
}