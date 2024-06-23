package com.example.book.controller;

import com.example.book.entity.Book;
import com.example.book.exception.ResourceNotFoundException;
import com.example.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<?> getAllBooks() {
        return new ResponseEntity<>(bookService.getBooks(), HttpStatus.OK);
    }

    @GetMapping(params = "authorId")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<?> getBooksByAuthor(@RequestParam("authorId") Integer authorId) {
        return new ResponseEntity<>(bookService.getBooksByAuthor(authorId), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> addBook(@RequestBody Book book) {
        return new ResponseEntity<>(bookService.addBook(book), HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> updateBook(@RequestBody Book book) {
        try {
            return new ResponseEntity<>(bookService.updateBook(book), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(params = "bookId")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> deleteBook(@RequestParam("bookId") Integer bookId) {
        try {
            bookService.deleteBook(bookId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
