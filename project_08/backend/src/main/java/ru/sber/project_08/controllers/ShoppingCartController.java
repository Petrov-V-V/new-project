package ru.sber.project_08.controllers;

import ru.sber.project_08.entities.Cart;
import ru.sber.project_08.entities.Product;
import ru.sber.project_08.services.ProductService;
import ru.sber.project_08.services.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

/**
 * Класс отвечающий за обработку запросов о тележке
 */
@Slf4j
@RestController
@RequestMapping("shopping-carts")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ShoppingCartController {
    CartService cartService;
    ProductService productService;

    public ShoppingCartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<Cart> addProduct(@PathVariable long id, @RequestBody Product product) {
        log.info("Добавление в корзину продукта {}", product);
        Optional<Product> optionalProduct = productService.findById(product.getId());
        if (!optionalProduct.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        //optionalProduct.get().setCount(1);
        Optional<Cart> shoppingCart = cartService.addProduct(id, optionalProduct.get(), 1);
        if (shoppingCart.isPresent()) {
            Cart cart = shoppingCart.get();
            return ResponseEntity.created(URI.create("/shopping-carts/"+cart.getId())).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/product/{idProduct}")
    public ResponseEntity<Cart> updateQuantity(@PathVariable long id, @PathVariable long idProduct,@RequestBody Product product) {
        log.info("Изменение количества товара в корзине");
        Optional<Cart> shoppingCart = cartService.updateCountOfProduct(id, idProduct, product.getCount());
        return shoppingCart.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id, @PathVariable long productId) {
        log.info("Удаление продукта из коризны", id);
        Optional<Product> optionalProduct = productService.findById(productId);
        if (!optionalProduct.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Product product = optionalProduct.get();
        Optional<Cart> optionalCart = cartService.findById(id);
        if (!optionalProduct.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Cart cart = optionalCart.get();
        boolean isDeleted = cartService.deleteProduct(cart, product);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}