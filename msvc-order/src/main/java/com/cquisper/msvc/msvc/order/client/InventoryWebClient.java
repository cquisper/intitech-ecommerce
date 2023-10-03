package com.cquisper.msvc.msvc.order.client;

import com.cquisper.msvc.msvc.order.dto.InventoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InventoryWebClient {

    private final WebClient.Builder webClientBuilder;

    public Flux<InventoryResponse> isInStock(List<String> productsCode){
        return this.webClientBuilder.build().get()
                .uri("http://msvc-inventory/stock", uriBuilder ->
                        uriBuilder.queryParam("product-code", productsCode).build())
                .retrieve()
                .bodyToFlux(InventoryResponse.class);
    }
}