package ru.sber.project_06.services;

import ru.sber.project_06.entities.ClientDTO;
import ru.sber.project_06.entities.Client;
import ru.sber.project_06.entities.Product;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для совершения процессов которые свяазаны с клиентом
 */
@Service
public interface ClientServiceInterface {
    long registrate(Client client);
    Optional<ClientDTO> findById(long id);
    boolean deleteById(long id);
}