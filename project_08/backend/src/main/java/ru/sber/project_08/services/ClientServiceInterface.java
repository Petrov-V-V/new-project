package ru.sber.project_08.services;

import ru.sber.project_08.entities.ClientDTO;
import ru.sber.project_08.entities.User;
import ru.sber.project_08.entities.Product;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для совершения процессов которые свяазаны с клиентом
 */
@Service
public interface ClientServiceInterface {
    long registrate(User client);
    Optional<ClientDTO> findById(long id);
    boolean deleteById(long id);
}