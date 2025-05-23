package com.example.library.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Поле 'ФИО' обязательно к заполнению")
    @Size(max = 255, message = "Поле 'ФИО' должно быть не длинее 100 символов.")
    private String fullName;

    @NotNull(message = "Поле 'Дата рождения' обязательно к заполнению")
    @Past(message = "Дата рождения должна быть в прошлом.")
    private LocalDate birthDate;

    public Client() {}

    public Client(String fullName, LocalDate birthDate) {
        this.fullName = fullName;
        this.birthDate = birthDate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
}