package ru.sber.project_06.services;

import ru.sber.project_06.entities.ClientDTO;
import ru.sber.project_06.entities.PaymentInfo;
import ru.sber.project_06.entities.Client;
import ru.sber.project_06.entities.Cart;
import ru.sber.project_06.entities.Cart;
import ru.sber.project_06.entities.Product;
import ru.sber.project_06.entities.ProductCart;
import ru.sber.project_06.repositories.ClientRepository;
import ru.sber.project_06.repositories.CartRepository;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final CartRepository cartRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository, CartRepository cartRepository) {
        this.clientRepository = clientRepository;
        this.cartRepository = cartRepository;
    }


    public long registrate(Client client) {
        Cart cart = new Cart();
        cart = cartRepository.save(cart); 

        client.setCart(cart);

        Client savedClient = clientRepository.save(client); 

        return savedClient.getId();
    }

    public Optional<ClientDTO> findById(long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);

        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            Cart cart = client.getCart();
            List<Product> products = fetchProductsForCart(cart.getId());
            return Optional.of(new ClientDTO(client.getName(), client.getUsername(), client.getEmail(), products));
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

    public boolean deleteById(long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);

        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
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