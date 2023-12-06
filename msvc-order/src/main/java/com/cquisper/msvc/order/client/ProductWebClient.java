package com.cquisper.msvc.order.client;

import com.cquisper.msvc.order.models.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProductWebClient {

    private final WebClient.Builder cartWebClient;

    public Product findProductById(String id) {
        return cartWebClient.build().get()
                .uri("http://msvc-products/find-by-id/{id}", id)
                .retrieve()
                .bodyToMono(Product.class)
                .block();
    }
}
