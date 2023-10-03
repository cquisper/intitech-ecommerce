package com.cquisper.msvc.authorization.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record AuthResponse(
        String email,
        String password,
        Boolean enabled,
        List<String> roles
) {
}
