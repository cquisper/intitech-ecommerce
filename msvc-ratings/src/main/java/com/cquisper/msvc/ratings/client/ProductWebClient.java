package com.cquisper.msvc.ratings.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class ProductWebClient {

    private final WebClient.Builder productWebClient;

    public Mono<Void> updateProduct(BigDecimal totalRating, String idProduct){
        return this.productWebClient.build().patch()
                .uri("http://msvc-products/update/{id}", idProduct)
                .bodyValue(totalRating)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
