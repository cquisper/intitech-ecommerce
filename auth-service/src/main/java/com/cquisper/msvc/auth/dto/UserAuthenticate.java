package com.cquisper.msvc.auth.dto;

import java.util.List;

public record UserAuthenticate(
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
