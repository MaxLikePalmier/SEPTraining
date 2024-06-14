package com.example.demo.repository;

import com.example.demo.entity.BookAuthor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookAuthorRepo extends JpaRepository<BookAuthor, Integer> {
}
