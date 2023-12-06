package com.cquisper.msvc.cart.client;

import com.cquisper.msvc.cart.models.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductWebClient {

    private final WebClient.Builder productWebClient;

    public Optional<Product> getProductById(String idProduct){
        return this.productWebClient.build().get()
                .uri("http://msvc-products/find-by-id/{id}", idProduct)
                .retrieve()
                .bodyToMono(Product.class)
                .blockOptional();
    }
}
