package ru.sber.project_08.repositories;

import ru.sber.project_08.entities.Cart;
import ru.sber.project_08.entities.Product;
import ru.sber.project_08.entities.ProductCart;

import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Репозиторий для извлечения информации о содержимом тележки
 */
@Repository
public interface ProductCartRepository extends JpaRepository<ProductCart, Long> {
    @Transactional
    int deleteByCartAndProduct(Cart cart, Product product);
    
    Optional<ProductCart> findByCartIdAndProductId(Long cartId, Long productId);

    List<ProductCart> findByCart(Cart cart);

    void deleteByCart(Cart cart);
}