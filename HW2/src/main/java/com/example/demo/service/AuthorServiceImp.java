package com.example.demo.service;

import com.example.demo.entity.Author;
import com.example.demo.repository.AuthorRepo;
import com.example.demo.repository.BookRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthorServiceImp implements AuthorService {
    private final AuthorRepo authorRepo;
    private final BookRepo bookRepo;

    public AuthorServiceImp(AuthorRepo authorRepo, BookRepo bookRepo) {
        this.authorRepo = authorRepo;
        this.bookRepo = bookRepo;
    }

    @Override
    public List<Author> getAuthors() {
        return authorRepo.findAll();
    }

    @Override
    public Set<Author> getAuthorsByBook(Integer bookId) {
        return bookRepo.findById(bookId).get().getAuthors();
    }

    @Override
    public Optional<Author> getAuthorsByName(String author){
        return authorRepo.findByName(author);
    }

    @Override
    public Author addAuthor(Author author) {
        return authorRepo.save(author);
    }

    @Override
    public Author updateAuthor(Author author) {
        Author old = authorRepo.findById(author.getId()).get();
        try {
            old.setName(author.getName());
        } catch (NullPointerException e) {
            throw new RuntimeException("Author not found");
        }
        return authorRepo.save(old);
    }

    @Override
    public void deleteAuthor(Author author) {
        Author old = authorRepo.findById(author.getId()).get();
        try {
            authorRepo.delete(old);
        } catch (NullPointerException e) {
            throw new RuntimeException("Author not found");
        }
    }
}
