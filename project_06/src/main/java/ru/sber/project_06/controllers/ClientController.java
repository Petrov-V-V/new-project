package ru.sber.project_06.controllers;

import ru.sber.project_06.entities.Client;
import ru.sber.project_06.entities.ClientDTO;
import ru.sber.project_06.repositories.ClientRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Класс отвечающий за обработку запросов о клиенте
 */
@Slf4j
@RestController
@RequestMapping("clients")
public class ClientController {

    private ClientRepository clientRepository;

    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @PostMapping
    public long registrateProduct(@RequestBody Client client) {
        log.info("Регистрация клиента {}", client);

        return clientRepository.registrate(client);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClient(@PathVariable long id) {
        log.info("Получение информации о клиенте по id {}", id);
        Optional<ClientDTO> client = clientRepository.findById(id);

        if (client.isPresent()) {
            ClientDTO clientDTO = client.get();
            return ResponseEntity.ok().body(clientDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable long id) {
        log.info("Удаление клиента по id {}", id);
        boolean isDeleted = clientRepository.deleteById(id);

        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
