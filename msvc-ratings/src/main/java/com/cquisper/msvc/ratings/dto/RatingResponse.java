package com.cquisper.msvc.ratings.dto;

import com.cquisper.msvc.ratings.models.User;
import lombok.Builder;

@Builder
public record RatingResponse(
        String id,
        Integer star,
        String comment,
        User user
) {
}
