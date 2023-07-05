package ru.sber.project_08.services;

import ru.sber.project_08.entities.Product;
import ru.sber.project_08.entities.Cart;
import ru.sber.project_08.entities.CartProducts;
import ru.sber.project_08.entities.Client;
import ru.sber.project_08.entities.PaymentInfo;
import ru.sber.project_08.entities.ProductCart;
import ru.sber.project_08.repositories.CartRepository;
import ru.sber.project_08.repositories.ClientRepository;
import ru.sber.project_08.repositories.ProductCartRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Класс для совершения процессов которые свяазаны с тележкой
 */
@Service
public class CartService implements CartServiceInterface {
    private final ClientRepository clientRepository;
    private final CartRepository cartRepository;
    private final ProductCartRepository productCartRepository;

    @Autowired
    public CartService(CartRepository cartRepository, ProductCartRepository productCartRepository, ClientRepository clientRepository) {
        this.cartRepository = cartRepository;
        this.productCartRepository = productCartRepository;
        this.clientRepository = clientRepository;
    }

    public Optional<Cart> addProduct(long cartId, Product product, int count) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);

        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            ProductCart productCart = new ProductCart(0, product, cart, count);
            productCartRepository.save(productCart);

            return Optional.of(cart);
        }

        return Optional.empty();
    }

    public Optional<Cart> updateCountOfProduct(long cartId, long productId, int quantity) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);

        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            Optional<ProductCart> optionalProductCart = productCartRepository.findByCartIdAndProductId(cartId, productId);

            if (optionalProductCart.isPresent()) {
                ProductCart productCart = optionalProductCart.get();
                productCart.setCount(quantity);
                productCartRepository.save(productCart);

                return Optional.of(cart);
            }
        }

        return Optional.empty();
    }

    public boolean deleteProduct(Cart cart, Product product) {
        int rowsDeleted = productCartRepository.deleteByCartAndProduct(cart, product);
        return rowsDeleted > 0;
    }

    public Optional<Cart> findById(long id) {
        return cartRepository.findById(id);
    }

    public double calcTotalSum(PaymentInfo paymentInfo) {
    Long clientId = paymentInfo.getUserId();
    Optional<Client> optionalClient = clientRepository.findById(clientId);

    if (optionalClient.isPresent()) {
        Client client = optionalClient.get();
        Long cartId = (long) client.getId();
        List<ProductCart> productCarts = cartRepository.findProductCartsByCartId(cartId);

        double totalCartPrice = productCarts.stream()
                .mapToDouble(pc -> pc.getProduct().getPrice().doubleValue() * pc.getCount())
                .sum();

        return totalCartPrice;
    }

    throw new RuntimeException("Payment can't be done due to empty cart");
    }
    
public List<CartProducts> getCartProducts(long userId) {
    Optional<Client> optionalClient = clientRepository.findById(userId);
    
    if (optionalClient.isPresent()) {
        Client client = optionalClient.get();
        Cart cart = client.getCart();
        
        if (cart != null) {
            List<ProductCart> productCarts = productCartRepository.findByCart(cart);
            List<CartProducts> cartProducts = new ArrayList<>();
            
            for (ProductCart productCart : productCarts) {
                Product product = productCart.getProduct();
                int quantity = productCart.getCount();
                
                CartProducts cartProduct = new CartProducts(
                        product.getId(),
                        product.getCount(),
                        quantity
                );
                cartProducts.add(cartProduct);
            }
            
            return cartProducts;
        }
    }

    return Collections.emptyList();
}




    
}