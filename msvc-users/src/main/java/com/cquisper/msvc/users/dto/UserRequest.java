package com.cquisper.msvc.users.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record UserRequest(
        String firstName,
        String lastName,
        String email,
        String mobile,
        String password,
        List<String> roles
) {
}
