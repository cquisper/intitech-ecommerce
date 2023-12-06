package com.cquisper.msvc.order.client;

import com.cquisper.msvc.order.dto.InventoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class InventoryWebClient {

    private final WebClient.Builder webClientBuilder;

    public Optional<List<InventoryResponse>> isInStock(List<String> productIds){
        return this.webClientBuilder.build().get()
                .uri("http://msvc-inventory/stock", uriBuilder ->
                        uriBuilder.queryParam("product-id", productIds).build())
                .retrieve()
                .bodyToFlux(InventoryResponse.class)
                .collectList()
                .blockOptional();
    }

    public Optional<List<InventoryResponse>> updateInventories(List<InventoryResponse> inventories){
        return this.webClientBuilder.build().put()
                .uri("http://msvc-inventory/update-all")
                .body(Flux.fromIterable(inventories), InventoryResponse.class)
                .retrieve()
                .bodyToFlux(InventoryResponse.class)
                .collectList()
                .blockOptional();
    }
}