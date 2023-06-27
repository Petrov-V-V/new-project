package ru.sber.project_05.repository;

import java.util.List;

import ru.sber.project_05.model.Book;

public interface BookRepositoryInterface {
    List<Book> findAll();
}
