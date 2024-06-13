package com.example.demo.repository;

import com.example.demo.entity.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MyBookRepoImp implements MyBookRepo<Book,Integer> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Book save(Book entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public Optional<Book> findById(Integer integer) {
        return Optional.ofNullable(entityManager.find(Book.class, integer));
    }

    @Override
    public List<Book> findAll() {
        return entityManager.createQuery("select b from Book b", Book.class).getResultList();
    }

    @Override
    public void delete(Book entity) {
        entityManager.remove(entity);
    }
}
