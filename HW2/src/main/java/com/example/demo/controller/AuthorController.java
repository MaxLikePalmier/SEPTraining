package com.example.demo.controller;

import com.example.demo.entity.Author;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("author")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<?> getAllAuthors() {
        return new ResponseEntity<>(authorService.getAuthors(), HttpStatus.OK);
    }

    @GetMapping(params = "bookId")
    public ResponseEntity<?> getAuthorsByBook(@RequestParam("bookId") Integer bookId) {
        return new ResponseEntity<>(authorService.getAuthorsByBook(bookId), HttpStatus.OK);
    }

    @GetMapping(params = "authorName")
    public ResponseEntity<?> getAuthorsByName(@RequestParam("authorName") String authorName) {
        return new ResponseEntity<>(authorService.getAuthorsByName(authorName), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateAuthor(@RequestBody Author author) {
        try {
            return new ResponseEntity<>(authorService.updateAuthor(author), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(params = "authorId")
    public ResponseEntity<?> deleteAuthor(@RequestParam("authorId") Integer authorId) {
        try {
            authorService.deleteAuthor(authorId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
