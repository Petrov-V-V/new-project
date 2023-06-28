package ru.sber.project_06.entities;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Интерфейс для хранения данных о продукте
 */
@Data
@AllArgsConstructor
public class Product {
    private long id;
    private String name;
    private BigDecimal price;
    private int quantity;
}
