package com.cquisper.msvc.auth.dto;

public record AuthRequest(
        String email,
        String password) {
}
