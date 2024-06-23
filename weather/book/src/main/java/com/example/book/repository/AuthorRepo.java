package com.example.book.repository;

import com.example.book.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface AuthorRepo extends JpaRepository<Author, Integer> {
    @Query("SELECT a FROM Author a WHERE a.name = :name")
    Optional<Author> findByName(String name);

    @Query("SELECT a FROM Author a JOIN a.books b WHERE b.id = :bookId")
    Set<Author> getAuthorsByBook(Integer bookId);
}
