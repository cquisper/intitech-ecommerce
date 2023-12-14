package com.cquisper.msvc.products.client;

import com.cquisper.msvc.products.models.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class InventoryWebClient {

    private final WebClient.Builder inventoryWebClient;

    public Mono<Inventory> createInventory(Inventory inventory) {
        return this.inventoryWebClient.build().post()
                .uri("http://msvc-inventory/create")
                .body(Mono.just(inventory), Inventory.class)
                .retrieve()
                .bodyToMono(Inventory.class);
    }

    public Mono<Inventory> findByProductId(String productId) {
        return this.inventoryWebClient.build().get()
                .uri("http://msvc-inventory/find-by-product-id/{productId}", productId)
                .retrieve()
                .bodyToMono(Inventory.class);
    }

    public Mono<Inventory> updateInventory(Map<String, Integer> fields, String id) {
        return this.inventoryWebClient.build().patch()
                .uri("http://msvc-inventory/update/{id}", id)
                .bodyValue(fields)
                .retrieve()
                .bodyToMono(Inventory.class);
    }
}