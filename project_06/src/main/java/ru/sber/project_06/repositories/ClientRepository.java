package ru.sber.project_06.repositories;

import ru.sber.project_06.entities.Client;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Репозиторий для извлечения информации о клиентах
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}