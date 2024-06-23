package com.example.security.controller;

import com.example.security.jwt.JwtUtility;
import com.example.security.entity.LoginForm;
import com.example.security.service.UserDetailsServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtility jwtUtility;
    private final UserDetailsServiceImp userDetailsService;

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("/authenticate")
    public String authenticate(@RequestBody LoginForm loginForm){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginForm.username(), loginForm.password()
        ));
        if (authentication.isAuthenticated()) {
            return jwtUtility.generateToken(userDetailsService.loadUserByUsername(loginForm.username()));
        } else {
            throw new UsernameNotFoundException("Invalid credentials");
        }
    }
}
