package com.example.library.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Поле 'Название' обязательно к заполнению")
    @Size(max = 255, message = "Поле 'Название' должно быть не длинее 100 символов.")
    private String title;

    @NotBlank(message = "Поле 'Автор' обязательно к заполнению")
    @Size(max = 255, message = "Поле 'Автор' должно быть не длинее 255 символов.")
    private String author;

    @NotBlank(message = "Поле 'ISBN' обязательно к заполнению")
    @Column(unique = true)
    private String isbn;

    public Book() {}

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
}