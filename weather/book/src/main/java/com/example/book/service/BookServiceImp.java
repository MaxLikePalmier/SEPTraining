package com.example.book.service;

import com.example.book.entity.Author;
import com.example.book.entity.Book;
import com.example.book.exception.ResourceNotFoundException;
import com.example.book.repository.AuthorRepo;
import com.example.book.repository.BookRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookServiceImp implements BookService {

    private final AuthorRepo authorRepo;
    private final BookRepo bookRepo;

    @Autowired
    public BookServiceImp(BookRepo bookRepo,AuthorRepo authorRepo) {
        this.authorRepo = authorRepo;
        this.bookRepo = bookRepo;
    }

    @Override
    public List<Book> getBooks() {
        return bookRepo.findAll();
    }

    @Override
    public Set<Book> getBooksByAuthor(Integer authorId) {
        return authorRepo.findById(authorId).get().getBooks();
    }

    @Override
    @Transactional
    public Book addBook(Book book) {
        Set<Author> processedAuthors = new HashSet<>();

        for (Author author : book.getAuthors()) {
            if (author.getId() != null) {
                Author existingAuthor = authorRepo.findById(author.getId())
                        .orElseThrow(() -> new IllegalArgumentException("Author with ID: " + author.getId() + " not found"));
                if (!existingAuthor.getName().equals(author.getName())) {
                    throw new IllegalArgumentException("Author ID: " + author.getId() + " does not match the provided name");
                }
                processedAuthors.add(existingAuthor);
            } else {
                processedAuthors.add(authorRepo.save(author));
            }
        }
        book.setAuthors(processedAuthors);
        return bookRepo.save(book);
    }

    @Override
    public Book updateBook(Book book) {
        Book old = bookRepo.findById(book.getId()).get();
        try {
            old.setTitle(book.getTitle());
        } catch (NullPointerException e) {
            throw new ResourceNotFoundException("Book not found");
        }
        return bookRepo.save(old);
    }

    @Override
    public void deleteBook(Integer id) {
        Book old = bookRepo.findById(id).get();
        try {
            bookRepo.delete(old);
        } catch (NullPointerException e) {
            throw new ResourceNotFoundException("Book not found");
        }
    }
}
