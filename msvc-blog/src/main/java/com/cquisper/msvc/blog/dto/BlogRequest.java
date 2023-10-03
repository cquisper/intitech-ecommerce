package com.cquisper.msvc.blog.dto;

import lombok.Builder;

@Builder
public record BlogRequest(
        String title,
        String category,
        String description
) {
}
