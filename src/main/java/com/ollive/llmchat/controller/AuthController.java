package com.ollive.llmchat.controller;

import com.ollive.llmchat.dto.JwtResponse;
import com.ollive.llmchat.dto.LoginRequest;
import com.ollive.llmchat.entity.User;
import com.ollive.llmchat.security.JwtService;
import com.ollive.llmchat.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String register(@RequestBody User user) {

        System.out.println(user.getName());
        System.out.println(user.getEmail());
        System.out.println(user.getPassword());

        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        userService.save(user);

        return "User registered successfully";
    }

    @PostMapping("/login")
    public JwtResponse login(
            @RequestBody LoginRequest request
    ) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userService
                .findByEmail(request.getEmail());

        String token = jwtService.generateToken(
                user.getEmail()
        );

        return new JwtResponse(token);
    }
}