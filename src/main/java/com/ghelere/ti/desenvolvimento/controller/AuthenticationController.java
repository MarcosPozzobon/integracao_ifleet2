package com.ghelere.ti.desenvolvimento.controller;

import com.ghelere.ti.desenvolvimento.controller.request.AuthenticationRequest;
import com.ghelere.ti.desenvolvimento.controller.request.RegisterRequest;
import com.ghelere.ti.desenvolvimento.controller.response.LoginResponse;
import com.ghelere.ti.desenvolvimento.entity.User;
import com.ghelere.ti.desenvolvimento.exception.*;
import com.ghelere.ti.desenvolvimento.repository.UserRepository;
import com.ghelere.ti.desenvolvimento.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    private final String[] AUTHORIZED_ROLES = {"ADMIN", "USER"};

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid final AuthenticationRequest request) {
        User existingUser = userRepository.findUserByLogin(request.login());
        if (existingUser == null) {
            throw new UsernameNotFoundException("O login fornecido não existe.");
        }

        var usernamePassword = new UsernamePasswordAuthenticationToken(request.login(), request.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponse(token));

    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid final RegisterRequest createUserJsonPost, HttpServletRequest requestInfo) {
        if (userRepository.findByLogin(createUserJsonPost.login()) != null) {
            throw new ExistingUserException("Erro ao criar um usuário.");
        }
        if (createUserJsonPost.login() == null || createUserJsonPost.login().isEmpty()) {
            throw new InvalidLoginCreationException("Forneça um login válido na hora de salvar.");
        }
        if (createUserJsonPost.password() == null || createUserJsonPost.password().isEmpty()) {
            throw new InvalidPasswordCreationException("Forneça uma senha válida.");
        }
        if (createUserJsonPost.role() == null || createUserJsonPost.role().isEmpty()) {
            throw new InvalidRoleCreationException("Forneça uma role válida.");
        }

        if (!Arrays.asList(AUTHORIZED_ROLES).contains(createUserJsonPost.role().toUpperCase())) {
            throw new InvalidRoleCreationException("Role fornecida não é válida.");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(createUserJsonPost.password());
        User user = new User(createUserJsonPost.login(), encryptedPassword, createUserJsonPost.role());

        userRepository.save(user);
        return ResponseEntity.ok().build();
    }
}