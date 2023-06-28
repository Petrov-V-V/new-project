package ru.sber.project_06.repositories;

import ru.sber.project_06.entities.ShoppingCart;
import ru.sber.project_06.entities.Product;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Класс для хранения и обработки данных о тележке
 */
@Repository
public class ShoppingCartRepository implements ShoppingCartRepositoryInteface {
    List<ShoppingCart> shoppingCarts = new ArrayList<>(List.of(new ShoppingCart(1L, "324ffds", new ArrayList<>(List.of(new Product(1l, "Яблоко", BigDecimal.valueOf(50), 0))))));

    @Override
    public Optional<ShoppingCart> addProduct(long id, Product product) {
        Optional<ShoppingCart> shoppingCart = shoppingCarts.stream().filter(c -> c.getId()==id).findFirst();
        if(shoppingCart.isPresent()){
            shoppingCart.get().getProducts().add(product);
        }
        return shoppingCart;
    }

    @Override
    public boolean deleteProduct(long id, long productId) {
        Optional<ShoppingCart> shoppingCart = shoppingCarts.stream().filter(c -> c.getId() == id).findFirst();
        if (shoppingCart.isPresent()) {
            return shoppingCart.get().getProducts().removeIf(product -> product.getId() == productId);
        }
        return false;
    }

    @Override
    public Optional<ShoppingCart> updateQuantityOfProduct(long id, long productId, int quantity) {
        Optional<ShoppingCart> shoppingCart = shoppingCarts.stream().filter(c -> c.getId() == id).findFirst();
        if (shoppingCart.isPresent()) {
            Optional<Product> product = shoppingCart.get().getProducts().stream().filter(p -> p.getId() == productId).findFirst();
            product.ifPresent(value -> value.setQuantity(quantity));
        }
        return shoppingCart;
    }

    public ShoppingCart generate(long id){
        ShoppingCart shoppingCart = new ShoppingCart(id, "g45wrewg", new ArrayList<>());
        shoppingCarts.add(shoppingCart);
        return shoppingCart;
    }
    
}