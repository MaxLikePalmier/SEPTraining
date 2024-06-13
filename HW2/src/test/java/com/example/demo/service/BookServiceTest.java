package com.example.demo.service;

import com.example.demo.entity.Author;
import com.example.demo.entity.Book;
import com.example.demo.repository.AuthorRepo;
import com.example.demo.repository.BookRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BookServiceTest {
    @Mock
    private BookRepo bookRepo;
    @Mock
    private AuthorRepo authorRepo;
    @InjectMocks
    private BookServiceImp bookService;

    @Test
    public void BookService_AddBook() {
        List<Author> authors = List.of(
                Author.builder().id(1).name("Author 1").build(),
                Author.builder().id(2).name("Author 2").build(),
                Author.builder().id(3).name("Author 3").build()
        );
        Author author = Author.builder().id(1).name("Author 1").build();
        Book book = Book.builder()
                .title("Book Title")
                .authors(
                        Set.of(
                                author,
                                Author.builder().name("Author 4").build()
                        )
                )
                .build();
        when(authorRepo.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(authors.get(author.getId()-1)));
        when(bookRepo.save(Mockito.any(Book.class))).thenReturn(book);
        Book savedBook = bookService.addBook(book);
        assertNotNull(savedBook);
        assertEquals(savedBook.getAuthors().size(), 2);
    }

    @Test
    public void BookService_AddBook_WrongAuthorID() {
        List<Author> authorsList = List.of(
                Author.builder().id(1).name("Author 1").build(),
                Author.builder().id(2).name("Author 2").build(),
                Author.builder().id(3).name("Author 3").build()
        );
        Author wrongAuthor = Author.builder().id(1).name("Author 3").build();
        Book book = Book.builder()
                .title("Book Title")
                .authors(
                        Set.of(
                                wrongAuthor,
                                Author.builder().name("Author 4").build()
                        )
                )
                .build();
        when(authorRepo.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(authorsList.get(wrongAuthor.getId() - 1)));
        when(bookRepo.save(Mockito.any(Book.class))).thenReturn(book);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> bookService.addBook(book));
        assertEquals("Author ID: " + wrongAuthor.getId() + " does not match the provided name", exception.getMessage());
    }
}
