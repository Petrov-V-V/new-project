package ru.sber.project_06.services;

import ru.sber.project_06.entities.Product;
import ru.sber.project_06.entities.Cart;
import ru.sber.project_06.entities.CartProducts;
import ru.sber.project_06.entities.PaymentInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для совершения процессов которые свяазаны с тележкой
 */
@Service
public interface CartServiceInterface {
    Optional<Cart> addProduct(long cartId, Product product, int count);
    Optional<Cart> updateCountOfProduct(long cartId, long productId, int quantity);
    boolean deleteProduct(Cart cart, Product product);
    double calcTotalSum(PaymentInfo paymentInfo);
    List<CartProducts> getCartProducts(long userId);
    
}