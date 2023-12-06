package com.cquisper.msvc.products.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        List<ProductResponse> wishList
) {
}
