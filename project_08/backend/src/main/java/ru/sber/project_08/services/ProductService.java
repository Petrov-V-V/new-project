package ru.sber.project_08.services;

import ru.sber.project_08.entities.Product;
import ru.sber.project_08.repositories.ProductCartRepository;
import ru.sber.project_08.repositories.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Класс для совершения процессов которые свяазаны с продуктами
 */
@Service
public class ProductService implements ProductServiceInterface{
    private final ProductRepository productRepository;
    private final ProductCartRepository productCartRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductCartRepository productCartRepository) {
        this.productRepository = productRepository;
        this.productCartRepository = productCartRepository;
    }

    public long save(Product product) {
        System.out.println(product);
        Product savedProduct = productRepository.save(product);

        return savedProduct.getId();
    }

    public Optional<Product> findById(long id) {
        return productRepository.findById(id);
    }

    public List<Product> findAll(String name) {
        return productRepository.findAll();
    }

    public boolean update(Product product) {
        productRepository.save(product);

        return true;
    }

    @Transactional
    public boolean deleteById(long id) {
        productCartRepository.deleteByProductId(id);
        productRepository.deleteById(id);

        return true;
    }

    public void updateProductCount(long productId, int quantity) {
        productRepository.findById(productId).ifPresent(product -> {
        product.setCount(product.getCount() - quantity);
        productRepository.save(product);
    });
}
}