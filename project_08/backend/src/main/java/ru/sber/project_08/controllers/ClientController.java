package ru.sber.project_08.controllers;

import ru.sber.project_08.entities.Client;
import ru.sber.project_08.entities.ClientDTO;
import ru.sber.project_08.entities.ProductCart;
import ru.sber.project_08.repositories.ClientRepository;
import ru.sber.project_08.services.ClientService;
import ru.sber.project_08.services.ProductService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

/**
 * Класс отвечающий за обработку запросов о клиенте
 */
@Slf4j
@RestController
@RequestMapping("clients")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ClientController {

    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<?> registrateClient(@RequestBody Client client) {
        log.info("Регистрация клиента {}", client);

        return ResponseEntity.created(URI.create("/client/"+clientService.registrate(client))).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClient(@PathVariable long id) {
        log.info("Получение информации о клиенте по id {}", id);
        Optional<ClientDTO> client = clientService.findById(id);

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
        boolean isDeleted = clientService.deleteById(id);

        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
