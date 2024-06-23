package com.example.security.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyUser {
    private String username;
    private String password;
    private String role;
}
