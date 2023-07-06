package ru.sber.project_08.services;

import ru.sber.project_08.entities.ClientDTO;
import ru.sber.project_08.entities.User;
import ru.sber.project_08.entities.Cart;
import ru.sber.project_08.entities.Product;
import ru.sber.project_08.entities.ProductCart;
import ru.sber.project_08.repositories.UserRepository;
import ru.sber.project_08.repositories.CartRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Класс для совершения процессов которые свяазаны с клиентом
 */
@Service
public class ClientService implements ClientServiceInterface {
    private final UserRepository clientRepository;
    private final CartRepository cartRepository;

    @Autowired
    public ClientService(UserRepository clientRepository, CartRepository cartRepository) {
        this.clientRepository = clientRepository;
        this.cartRepository = cartRepository;
    }


    public long registrate(User client) {
        Cart cart = new Cart();
        cart = cartRepository.save(cart); 

        client.setCart(cart);

        User savedClient = clientRepository.save(client); 

        return savedClient.getId();
    }

    public Optional<ClientDTO> findById(long id) {
        Optional<User> optionalClient = clientRepository.findById(id);

        if (optionalClient.isPresent()) {
            User client = optionalClient.get();
            Cart cart = client.getCart();
            List<Product> products = fetchProductsForCart(cart.getId());
            return Optional.of(new ClientDTO((long) client.getId(), client.getName(), client.getUsername(), client.getEmail(), products));
        }

        return Optional.empty();
    }

    public Optional<ClientDTO> findByEmailAndPassword(String email, String password) {
        Optional<User> optionalClient = clientRepository.findByEmailAndPassword(email, password);
        
        if (optionalClient.isPresent()) {
            User client = optionalClient.get();
            Cart cart = client.getCart();
            List<Product> products = fetchProductsForCart(cart.getId());
            return Optional.of(new ClientDTO((long) client.getId(), client.getName(), client.getUsername(), client.getEmail(), products));
        }

        return Optional.empty();
    }

    private List<Product> fetchProductsForCart(long cartId) {
        List<ProductCart> productCarts = cartRepository.findProductCartsByCartId(cartId);
        List<Product> products = new ArrayList<>();

        for (ProductCart productCart : productCarts) {
            Product prod = productCart.getProduct();
            prod.setCount(productCart.getCount());
            products.add(prod);
        }

        return products;
    }

    @Transactional
    public boolean deleteById(long id) {
        Optional<User> optionalClient = clientRepository.findById(id);

        if (optionalClient.isPresent()) {
            User client = optionalClient.get();
            Cart cart = client.getCart();

            clientRepository.delete(client);

            if (cart != null) {
                deleteProductCartsByCartId(cart.getId());
                cartRepository.delete(cart);
            }
            return true;
        } else {
            throw new RuntimeException();
        }
    }

    private void deleteProductCartsByCartId(long cartId) {
        cartRepository.deleteProductCartsByCartId(cartId);
    }

}