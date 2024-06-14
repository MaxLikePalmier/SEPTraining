package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 *  Intermediary entity to represent the relationship
 *  book 1:m book_author_table m:1 author
 */
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
    @JoinColumn(name = "book_id")
    private Book book;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;
}
