package com.ghelere.ti.desenvolvimento.controller.request;

public record AuthenticationRequest(
        String login,
        String password
) {
}
