package ru.sber.project_06.services;

import ru.sber.project_06.entities.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для совершения процессов которые свяазаны с продуктами
 */
@Service
public interface ProductServiceInterface {
    long save(Product product);
    Optional<Product> findById(long id);
    List<Product> findAll(String name);
    boolean update(Product product);
    boolean deleteById(long id);
    void updateProductCount(long productId, int quantity);
}