package com.example.demo.service;

import com.example.demo.entity.Author;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AuthorService {
    List<Author> getAuthors();
    Set<Author> getAuthorsByBook(Integer bookId);
    Optional<Author> getAuthorsByName(String author);
    Author updateAuthor(Author author);
    void deleteAuthor(Integer id);
}
