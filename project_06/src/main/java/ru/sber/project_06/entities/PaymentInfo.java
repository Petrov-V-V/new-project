package ru.sber.project_06.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class PaymentInfo {
    long userId;
    BigDecimal sum;
}