package com.cquisper.msvc.ratings.controllers;

import com.cquisper.msvc.ratings.dto.RatingResponse;
import com.cquisper.msvc.ratings.dto.RatingRequest;
import com.cquisper.msvc.ratings.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @GetMapping("/all-by-product/{idProduct}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<RatingResponse> getAllRatingByIdProduct(@PathVariable String idProduct){
        return this.ratingService.getAllRatingByIdProduct(idProduct);
    }

    @PostMapping("/rating")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<RatingResponse> ratingProduct(@RequestBody RatingRequest ratingRequest, @RequestHeader("X-Email") String email){
        return this.ratingService.ratingToProduct(ratingRequest, email);
    }
}
