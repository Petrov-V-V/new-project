package ru.sber.project_06.controllers;

import ru.sber.project_06.entities.ShoppingCart;
import ru.sber.project_06.entities.Product;
import ru.sber.project_06.repositories.ProductRepository;
import ru.sber.project_06.repositories.ShoppingCartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("shopping-carts")
public class ShoppingCartController {
    ShoppingCartRepository cartRepository;
    ProductRepository productRepository;

    public ShoppingCartController(ShoppingCartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
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
        Optional<ShoppingCart> shoppingCart = cartRepository.addProduct(id, optionalProduct.get());
        if (shoppingCart.isPresent()) {
            return ResponseEntity.ok().body(shoppingCart.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/product/{idProduct}")
    public ResponseEntity<ShoppingCart> updateQuantity(@PathVariable long id, @PathVariable long idProduct,@RequestBody Product product) {
        log.info("Изменение количества товара в корзине");
        Optional<ShoppingCart> shoppingCart = cartRepository.updateQuantityOfProduct(id,idProduct, product.getQuantity());
        return shoppingCart.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id, @PathVariable long productId) {
        log.info("Удаление продукта из коризны", id);
        boolean isDeleted = cartRepository.deleteProduct(id, productId);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
