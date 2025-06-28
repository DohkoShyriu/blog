package com.dohko.blog.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "authors")
public class Author {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String surname;
    private String firstName;

    public Author() {
        super();
    }

    public Author(String surname, String firstName) {
        this.surname = surname;
        this.firstName = firstName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
