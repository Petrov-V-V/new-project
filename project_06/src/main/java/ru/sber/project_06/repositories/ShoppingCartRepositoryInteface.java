package ru.sber.project_06.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import ru.sber.project_06.entities.CartProducts;
import ru.sber.project_06.entities.PaymentInfo;
import ru.sber.project_06.entities.Product;
import ru.sber.project_06.entities.ShoppingCart;

/**
 * Интерфейс для хранения и обработки данных о тележке
 */
@Repository
public interface ShoppingCartRepositoryInteface {
    Optional<ShoppingCart> addProduct(long id, Product product);

    boolean deleteProduct(long id, long productId);

    Optional<ShoppingCart> updateQuantityOfProduct(long id, long productId, int quantity);

    double calcTotalSum(PaymentInfo paymentInfo);

    List<CartProducts> getCartProducts(long userId);
}