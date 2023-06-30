package ru.sber.project_06.repositories;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import ru.sber.project_06.entities.ClientDTO;
import ru.sber.project_06.entities.Client;

/**
 * Интерфейс для хранения и обработки данных о клиенте
 */
@Repository
public interface ClientRepositoryInteface {
    public long registrate(Client client);
    
    public Optional<ClientDTO> findById(long id);

    public boolean deleteById(long id);
}