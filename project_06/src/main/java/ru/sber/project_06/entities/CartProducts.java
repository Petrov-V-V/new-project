package ru.sber.project_06.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Класс для хранения данных о продуктах в тележке
 */
@Data
@AllArgsConstructor
public class CartProducts {
    private long id;
    private int  count;
    private int quantity;
}