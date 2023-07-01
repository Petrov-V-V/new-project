package ru.sber.project_06.repositories;

import ru.sber.project_06.entities.Cart;
import ru.sber.project_06.entities.ProductCart;

import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


/**
 * Репозиторий для извлечения информации о тележке
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("SELECT pc FROM ProductCart pc WHERE pc.cart.id = :cartId")
    List<ProductCart> findProductCartsByCartId(Long cartId);

    @Transactional
    @Modifying
    @Query("DELETE FROM ProductCart pc WHERE pc.cart.id = :cartId")
    void deleteProductCartsByCartId(Long cartId);
}