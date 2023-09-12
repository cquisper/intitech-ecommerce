package com.cquisper.msvc.authorization.dto;

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
