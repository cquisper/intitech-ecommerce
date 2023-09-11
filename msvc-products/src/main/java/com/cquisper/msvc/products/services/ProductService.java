package com.cquisper.msvc.products.services;

import com.cquisper.msvc.products.dto.ProductRequest;
import com.cquisper.msvc.products.dto.ProductResponse;
import com.cquisper.msvc.products.models.Product;
import com.cquisper.msvc.products.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public Flux<ProductResponse> getAllProducts(){
        return this.productRepository.findAll()
                .map(product -> ProductResponse.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .build());
    }

    public Mono<Map<String, Object>> createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .build();

        return this.productRepository.save(product)
                .map(productResponse -> {
                    String message = String.format("Product {%s} saved", productResponse.getId());
                    log.info(message);
                    return Map.of("message", message);
                });
    }
}
