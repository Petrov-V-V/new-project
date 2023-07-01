package ru.sber.project_06.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;


/**
 * Класс для хранения данных о тележках
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "carts", schema = "products_petrov_vv")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 255)
    private String promocode;
}