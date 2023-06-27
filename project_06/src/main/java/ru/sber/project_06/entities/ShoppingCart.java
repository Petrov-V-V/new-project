package ru.sber.project_06.entities;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Интерфейс для хранения данных о тележке
 */
@Data
@AllArgsConstructor
public class ShoppingCart {
    private Long id;
    private String promocode;
    private List<Product> products;
}
