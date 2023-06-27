package ru.sber.project_06.controllers;

import ru.sber.project_06.entities.ShoppingCart;
import ru.sber.project_06.entities.Product;
import ru.sber.project_06.repositories.ProductRepository;
import ru.sber.project_06.repositories.ShoppingCartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Класс отвечающий за обработку запросов о тележке
 */
@Slf4j
@RestController
@RequestMapping("shopping-carts")
public class ShoppingCartController {
    ShoppingCartRepository shoppingCartRepository;
    ProductRepository productRepository;

    public ShoppingCartController(ShoppingCartRepository cartRepository, ProductRepository productRepository) {
        this.shoppingCartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @PostMapping("/{id}")
    public ResponseEntity<ShoppingCart> addProduct(@PathVariable long id, @RequestBody Product product) {
        log.info("Добавление в корзину продукта {}", product);
        Optional<Product> optionalProduct = productRepository.findById(product.getId());
        if (!optionalProduct.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        optionalProduct.get().setQuantity(1);
        Optional<ShoppingCart> shoppingCart = shoppingCartRepository.addProduct(id, optionalProduct.get());
        if (shoppingCart.isPresent()) {
            return ResponseEntity.ok().body(shoppingCart.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/product/{idProduct}")
    public ResponseEntity<ShoppingCart> updateQuantity(@PathVariable long id, @PathVariable long idProduct,@RequestBody Product product) {
        log.info("Изменение количества товара в корзине");
        Optional<ShoppingCart> shoppingCart = shoppingCartRepository.updateQuantityOfProduct(id,idProduct, product.getQuantity());
        return shoppingCart.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id, @PathVariable long productId) {
        log.info("Удаление продукта из коризны", id);
        boolean isDeleted = shoppingCartRepository.deleteProduct(id, productId);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
