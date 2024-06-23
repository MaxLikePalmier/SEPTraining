package com.example.book.repository;

import com.example.book.entity.Book;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface MyBookRepo<T, ID> extends Repository<T, ID> {

    <S extends T> Book save(S entity);

    Optional<T> findById(ID id);

    List<T> findAll();

    void delete(T entity);
}