package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book_author_table")
public class BookAuthor {
    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne
    private Book book;
    @ManyToOne
    private Author author;
}
