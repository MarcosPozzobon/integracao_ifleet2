package com.ghelere.ti.desenvolvimento.exception.handler;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.ghelere.ti.desenvolvimento.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@RestController
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(JWTCreationException.class)
    public ResponseEntity<StandardError> handleJwtCreationError(JWTCreationException ex) {
        StandardError error = StandardError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.UNAUTHORIZED.value())
                .error("Ocorreu um erro ao gerar o token!")
                .message(ex.getMessage())
                .path("/auth/login")
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ExistingUserException.class)
    public ResponseEntity<StandardError> handleExistingUserException(ExistingUserException ex) {
        StandardError error = StandardError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("O usuário já existe.")
                .message(ex.getMessage())
                .path("/auth/register")
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<StandardError> handleLoginNotFoundException(UsernameNotFoundException ex) {
        StandardError error = StandardError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("O usuário fornecido não existe.")
                .message(ex.getMessage())
                .path("/auth/login")
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidLoginCreationException.class)
    public ResponseEntity<StandardError> handleInvalidLoginCreationException(InvalidLoginCreationException ex) {
        StandardError error = StandardError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Um erro ocorreu ao criar um novo usuário.")
                .message(ex.getMessage())
                .path("/auth/register")
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidPasswordCreationException.class)
    public ResponseEntity<StandardError> handleInvalidPasswordCreationException(InvalidPasswordCreationException ex) {
        StandardError error = StandardError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Um erro ocorreu ao criar um novo usuário.")
                .message(ex.getMessage())
                .path("/auth/register")
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidRoleCreationException.class)
    public ResponseEntity<StandardError> handleInvalidRoleCreationException(InvalidRoleCreationException ex) {
        StandardError error = StandardError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Um erro ocorreu ao criar um novo usuário.")
                .message(ex.getMessage())
                .path("/auth/register")
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

}
