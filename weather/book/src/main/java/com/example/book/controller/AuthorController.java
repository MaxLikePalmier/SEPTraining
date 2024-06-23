package com.example.book.controller;

import com.example.book.entity.Author;
import com.example.book.exception.ResourceNotFoundException;
import com.example.book.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book/author")
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
//    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<?> getAllAuthors() {
        return new ResponseEntity<>(authorService.getAuthors(), HttpStatus.OK);
    }

    @GetMapping(params = "bookId")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<?> getAuthorsByBook(@RequestParam("bookId") Integer bookId) {
        return new ResponseEntity<>(authorService.getAuthorsByBook(bookId), HttpStatus.OK);
    }

    @GetMapping(params = "authorName")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<?> getAuthorsByName(@RequestParam("authorName") String authorName) {
        return new ResponseEntity<>(authorService.getAuthorsByName(authorName), HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> updateAuthor(@RequestBody Author author) {
        try {
            return new ResponseEntity<>(authorService.updateAuthor(author), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(params = "authorId")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> deleteAuthor(@RequestParam("authorId") Integer authorId) {
        try {
            authorService.deleteAuthor(authorId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
