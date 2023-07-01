package ru.sber.project_06.services;

import ru.sber.project_06.entities.Product;
import ru.sber.project_06.repositories.ProductRepository;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
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

    public boolean deleteById(long id) {
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