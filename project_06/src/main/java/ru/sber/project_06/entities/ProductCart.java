package ru.sber.project_06.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

/**
 * Интерфейс для хранения части данных о клиенте
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products_carts", schema = "products_petrov_vv")
public class ProductCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_product", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "id_cart", nullable = false)
    private Cart cart;

    @Column(nullable = false)
    private Integer count;
}