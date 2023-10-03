package com.cquisper.msvc.products.client;

import com.cquisper.msvc.products.models.Rating;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class RatingWebClient {

    private final WebClient.Builder ratingWebClient;

    public Flux<Rating> getAllRatingByIdProduct(@PathVariable String idProduct){
        return this.ratingWebClient.build().get()
                .uri("http://msvc-ratings/all-by-product/{idProduct}", idProduct)
                .retrieve()
                .bodyToFlux(Rating.class);
    }

}
