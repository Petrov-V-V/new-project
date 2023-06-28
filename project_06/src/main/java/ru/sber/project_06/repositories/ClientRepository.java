package ru.sber.project_06.repositories;

import ru.sber.project_06.entities.Client;
import ru.sber.project_06.entities.Product;
import ru.sber.project_06.entities.ShoppingCart;
import ru.sber.project_06.repositories.ShoppingCartRepository;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Класс репозиторий для хранения и обработки данных о клиенте
 */
@Repository
public class ClientRepository implements ClientRepositoryInteface {
    private List<Client> clients = new ArrayList<>(List.of(
            new Client(1l, "Миша", "misha", "11111", "misha@w.e", new ShoppingCart(1L, "2jo4h9dfss", new ArrayList<>(List.of(new Product(1l, "Яблоко", BigDecimal.valueOf(50), 0)))))
    ));

    ShoppingCartRepository shoppingCartRepository;

    public ClientRepository(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    public long registrate(Client client) {
        long id = generateId();
        client.setId(id);
        client.setShoppingCart(shoppingCartRepository.generate(id));

        clients.add(client);
        return id;
    }

    private long generateId() {
        Random random = new Random();
        int low = 1;
        int high = 1_000_000;
        return random.nextLong(high - low) + low;
    }

    @Override
    public Optional<Client> findById(long id) {
        return clients.stream()
                .filter(client -> client.getId() == id)
                .findAny();
    }

    @Override
    public boolean deleteById(long id) {
        return clients.removeIf(client -> client.getId() == id);
    }
}