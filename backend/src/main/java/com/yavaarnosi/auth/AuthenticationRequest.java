package com.yavaarnosi.auth;

public record AuthenticationRequest(
        String username,
        String password

) {
}
