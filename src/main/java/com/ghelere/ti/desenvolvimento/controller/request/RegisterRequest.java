package com.ghelere.ti.desenvolvimento.controller.request;

public record RegisterRequest (
        String login,
        String password,
        String role
){
}
