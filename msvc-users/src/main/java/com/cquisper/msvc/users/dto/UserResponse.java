package com.cquisper.msvc.users.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record UserResponse(
        String username,

        String password,

        Boolean enabled,

        List<String> roles
) {
}
