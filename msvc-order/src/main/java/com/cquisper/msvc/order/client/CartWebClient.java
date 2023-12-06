package com.cquisper.msvc.order.client;

import com.cquisper.msvc.order.models.Cart;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Component @Slf4j
@RequiredArgsConstructor
public class CartWebClient {

    private final WebClient.Builder cartWebClient;

    public Optional<Cart> getCartByOrderBy(Long orderBy) {
        return cartWebClient.build().get()
                .uri("http://msvc-cart/find-by-oderby/{orderBy}", orderBy)
                .retrieve()
                .bodyToMono(Cart.class)
                .blockOptional();
    }
}
