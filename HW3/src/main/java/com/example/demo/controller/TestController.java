package com.example.demo.controller;

import com.example.demo.config.UserDetailsServiceImp;
import com.example.demo.jwt.JwtUtility;
import com.example.demo.jwt.LoginForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class TestController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtility jwtUtility;
    private final UserDetailsServiceImp userDetailsService;

    @GetMapping("/home")
    public String handleWelcome(){
        return "home";
    }

    @GetMapping("/admin/home")
    @PreAuthorize("hasRole('ADMIN')")
    public String handleAdminHome(){
        return "home_admin";
    }

    @GetMapping("/user/home")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String handleUserHome(){
        return "home_user";
    }

    @GetMapping("/login")
    public String handleLogin(){
        return "custom_login";
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
