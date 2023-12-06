package com.cquisper.msvc.gateway.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ClaimsResponse(
        String email,
        List<String> authorities
) {
}
