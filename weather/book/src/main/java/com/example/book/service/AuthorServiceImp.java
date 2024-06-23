package com.example.book.service;

import com.example.book.entity.Author;
import com.example.book.repository.AuthorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthorServiceImp implements AuthorService {
    private final AuthorRepo authorRepo;

    @Autowired
    public AuthorServiceImp(AuthorRepo authorRepo) {
        this.authorRepo = authorRepo;
    }

    @Override
    public List<Author> getAuthors() {
        return authorRepo.findAll();
    }

    @Override
    public Set<Author> getAuthorsByBook(Integer bookId) {
        return authorRepo.getAuthorsByBook(bookId);
    }

    @Override
    public Optional<Author> getAuthorsByName(String author){
        return authorRepo.findByName(author);
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
    public void deleteAuthor(Integer id) {
        Author old = authorRepo.findById(id).get();
        try {
            authorRepo.delete(old);
        } catch (NullPointerException e) {
            throw new RuntimeException("Author not found");
        }
    }
}
