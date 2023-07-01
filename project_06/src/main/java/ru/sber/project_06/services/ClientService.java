package ru.sber.project_06.services;

import ru.sber.project_06.entities.ClientDTO;
import ru.sber.project_06.entities.Client;
import ru.sber.project_06.entities.Cart;
import ru.sber.project_06.entities.Product;
import ru.sber.project_06.entities.ProductCart;
import ru.sber.project_06.repositories.ClientRepository;
import ru.sber.project_06.repositories.CartRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Класс для совершения процессов которые свяазаны с клиентом
 */
@Service
public class ClientService implements ClientServiceInterface {
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