package ru.sber.project_05.repository;

import ru.sber.project_05.model.Book;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс харнящий список текйщих книг и позваляющий его получать 
 */
@Repository
public class BookRepository implements BookRepositoryInterface {

  private List<Book> books = new ArrayList<>(List.of(new Book(BigDecimal.valueOf(949), "KGBT+", "Виктор Пелевин Олегович", "/1_1.png")
  , new Book(BigDecimal.valueOf(807), "Книга Ночи", "Блэк Х.", "/1_2.png")
  , new Book(BigDecimal.valueOf(1669), "Бэтмен и сын", "Моррисон Г., Джонс Дж., Рака Г., Уэйд М.", "/1_3.png")
  , new Book(BigDecimal.valueOf(726), "Дэдпул. Величайший комикс в мире. Том 6. Последняя капля", "Джерри Дагган", "/1_4.png")
  , new Book(BigDecimal.valueOf(1457), "Земля королей. Трефовый том", "Федор Нечитайло", "/2_1.png")
  , new Book(BigDecimal.valueOf(1580), "Земля королей. Червовый том", "Федор Нечитайло", "/2_2.png")
  , new Book(BigDecimal.valueOf(243), "1984", "Оруэлл Дж.", "/2_3.png")
  , new Book(BigDecimal.valueOf(631), "Дочь для волка", "Уиттен Ханна", "/2_4.png")
  , new Book(BigDecimal.valueOf(1190), "Атлант расправил плечи. В 3 книгах", "Рэнд А.", "/3_1.png")
  , new Book(BigDecimal.valueOf(1508), "Психбольница в руках пациентов. Алан Купер об интерфейсах", "Купер Алан", "/3_2.png")
  , new Book(BigDecimal.valueOf(571), "Что скрывают числа", "Маурицио Кодоньо", "/3_3.png")
  , new Book(BigDecimal.valueOf(985), "Думай медленно... решай быстро", "Канеман Даниэль", "/3_4.png")
  , new Book(BigDecimal.valueOf(779), "Боги и чудовища", "Махрин Шелби", "/4_1.png")
  , new Book(BigDecimal.valueOf(1112), "Бэтмен. Рептилия", "Эннис Гар", "/4_2.png")
  , new Book(BigDecimal.valueOf(739), "Искра (#3)", "Кеннеди Р.", "/4_3.png")
  , new Book(BigDecimal.valueOf(807), "Египетские хроники. Кольцо огня (#2)", "Вульф М.", "/4_4.png")));

  public List<Book> findAll() {
    return books;
  }

}