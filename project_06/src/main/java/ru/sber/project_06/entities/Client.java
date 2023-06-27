package ru.sber.project_06.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Интерфейс для хранения данных о клиенте
 */
@Data
@AllArgsConstructor
public class Client {
    private Long id;
    private String name;
    private String login;
    private String password;
    private String email;
    private ShoppingCart shoppingCart;
}
