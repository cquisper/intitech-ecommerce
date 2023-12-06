package com.cquisper.msvc.blog.dto;

import com.cquisper.msvc.blog.models.Image;
import lombok.Builder;

import java.util.List;

@Builder
public record BlogRequest(
        String title,
        String category,
        String description,
        List<Image> images
) {
}
