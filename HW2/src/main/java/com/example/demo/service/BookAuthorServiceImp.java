package com.example.demo.service;

import com.example.demo.entity.Author;
import com.example.demo.entity.Book;
import com.example.demo.entity.BookAuthor;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.AuthorRepo;
import com.example.demo.repository.BookAuthorRepo;
import com.example.demo.repository.BookRepo;

public class BookAuthorServiceImp implements BookAuthorService {

    private final BookRepo bookRepo;
    private final AuthorRepo authorRepo;
    private final BookAuthorRepo bookAuthorRepo;

    public BookAuthorServiceImp(BookRepo bookRepo, AuthorRepo authorRepo, BookAuthorRepo bookAuthorRepo) {
        this.bookRepo = bookRepo;
        this.authorRepo = authorRepo;
        this.bookAuthorRepo = bookAuthorRepo;
    }

    @Override
    public void addBook(Integer book_id, Integer author_id) {
        Book book = bookRepo.findById(book_id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        Author author = authorRepo.findById(author_id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found"));
        BookAuthor bookAuthor = new BookAuthor(null, book, author);
        bookAuthorRepo.save(bookAuthor);
    }
}
