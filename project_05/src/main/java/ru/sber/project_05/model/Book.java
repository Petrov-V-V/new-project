package ru.sber.project_05.model;

import java.math.BigDecimal;

/**
 * Класс хранящий данные о книге
 */
public class Book {
    private static Integer nextId = 0;
    private Integer id = 0;
    private BigDecimal price;
    private String name;
    private String author;
    private String img;

    public Book(BigDecimal price, String name, String author, String img) {
        this.id = nextId;
        nextId++;
        this.price = price;
        this.name = name;
        this.author = author;
        this.img = img;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
    
}
