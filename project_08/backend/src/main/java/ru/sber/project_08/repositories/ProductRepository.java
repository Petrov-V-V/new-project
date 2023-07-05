package ru.sber.project_08.repositories;

import ru.sber.project_08.entities.Product;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для извлечения информации о продуктах
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}