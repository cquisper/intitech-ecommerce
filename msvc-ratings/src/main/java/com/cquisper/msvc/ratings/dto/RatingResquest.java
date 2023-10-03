package com.cquisper.msvc.ratings.dto;

import lombok.Builder;

@Builder
public record RatingResquest(
        Integer star,
        String comment,
        Long idUser,
        String idProduct
) {
}
