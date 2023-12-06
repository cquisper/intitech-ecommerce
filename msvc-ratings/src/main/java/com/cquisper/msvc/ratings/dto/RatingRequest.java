package com.cquisper.msvc.ratings.dto;

import lombok.Builder;

@Builder
public record RatingRequest(
        Integer star,
        String comment,
        String idProduct
) {
}
