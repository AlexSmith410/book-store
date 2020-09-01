package com.geekbrains.book.store.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Getter
    @AllArgsConstructor
    public enum Genre{
        FANTASY("FANTASY"), FANTASTIC("FANTASTIC"), DETECTIVE("DETECTIVE");
        String name;
    }

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "publish_year")
    private int publishYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre")
    private Genre genre;
}
