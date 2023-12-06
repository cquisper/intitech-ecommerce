package com.cquisper.msvc.auth.dto;


import lombok.Builder;

@Builder
public record AuthResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String mobile,
        String token
) {
}
