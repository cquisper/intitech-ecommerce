package com.cquisper.msvc.users.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record AuthResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String password,
        String mobile,
        Boolean enabled,
        List<String> roles
) {
}
