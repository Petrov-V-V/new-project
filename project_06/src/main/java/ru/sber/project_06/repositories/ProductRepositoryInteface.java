package ru.sber.project_06.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import ru.sber.project_06.entities.Product;

/**
 * Интерфейс для хранения и обработки данных о продукте
 */
@Repository
public interface ProductRepositoryInteface {
    public long save(Product product);
    public Optional<Product> findById(long id);
    public List<Product> findAll(String name);
    public boolean update(Product product);
    public boolean deleteById(long id);
}