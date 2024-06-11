package com.example.demo.service;

import com.example.demo.entity.Book;

import java.util.List;
import java.util.Set;

public interface BookService {
    List<Book> getBooks();
    Set<Book> getBooksByAuthor(Integer authorId);
    Book addBook(Book book);
    Book updateBook(Book book);
    void deleteBook(Book book);
}
