package com.example.demo.dao;

import com.example.demo.entity.MyUser;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDao {
    private final static List<MyUser> USERS = Arrays.asList(
            MyUser.builder()
                    .username("user")
                    .password("$2a$12$i8gK1grTCqFSPx0eSNBZPuJo4NKnFZt4j5v1mhZZgC55NooxF67Pq")
                    .role("USER")
                    .build(),
            MyUser.builder()
                    .username("admin")
                    .password("$2a$12$i8gK1grTCqFSPx0eSNBZPuJo4NKnFZt4j5v1mhZZgC55NooxF67Pq")
                    .role("ADMIN,USER")
                    .build()
    );

    public Optional<MyUser> findByUsername(String username) {
        return USERS
                .stream()
                .filter(myUser -> myUser.getUsername().equals(username))
                .findFirst();
    }
}
