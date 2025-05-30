package com.cquisper.msvc.users.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String mobile,
        Boolean enabled,
        List<String> wishList,
        String address,
        List<String> roles,
        String refreshToken,
        String photo
) {
}
