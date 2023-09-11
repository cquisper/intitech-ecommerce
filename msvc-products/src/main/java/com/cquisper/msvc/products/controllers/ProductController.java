package com.cquisper.msvc.products.controllers;

import com.cquisper.msvc.products.dto.ProductRequest;
import com.cquisper.msvc.products.dto.ProductResponse;
import com.cquisper.msvc.products.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public Flux<ProductResponse> getAllProducts() {
        return this.productService.getAllProducts();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Map<String, Object>> createProduct(@RequestBody ProductRequest productRequest) {
        return this.productService.createProduct(productRequest);
    }
}
