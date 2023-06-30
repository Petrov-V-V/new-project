package ru.sber.project_06.repositories;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import ru.sber.project_06.entities.PaymentInfo;
import ru.sber.project_06.entities.Product;
import ru.sber.project_06.entities.ShoppingCart;

/**
 * Интерфейс для хранения и обработки данных о платеже
 */
@Repository
public interface PaymentRepositoryInteface {
    boolean pay(PaymentInfo paymentInfo);
}