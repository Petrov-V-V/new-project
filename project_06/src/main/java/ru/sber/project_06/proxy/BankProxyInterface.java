package ru.sber.project_06.proxy;

import java.math.BigDecimal;

/**
 * Интерфейс для банка
 */
public interface BankProxyInterface {
    boolean checkMeansCustomer(String numberCard, BigDecimal sum);
}