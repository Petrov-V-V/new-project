package ru.sber.project_06.entities;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс для хранения части данных о клиенте
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {
    private String name;
    private String login;
    private String email;
    private List<Product> cart;
}