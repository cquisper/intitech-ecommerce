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

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Flux<ProductResponse> getAllProducts() {
        return this.productService.getAllProducts();
    }

    @GetMapping("/find-by-id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ProductResponse> getProductById(@PathVariable String id) {
        return this.productService.getProductById(id);
    }

    @GetMapping("/all-with-rating")
    @ResponseStatus(HttpStatus.OK)
    public Flux<ProductResponse> getAllProductsWithRating() {
        return this.productService.getAllProductsWithRating();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        return this.productService.createProduct(productRequest);
    }

    @PatchMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ProductResponse> updateProduct(@RequestBody Map<Object, Object> fields, @PathVariable String id) {
        return this.productService.updateProduct(fields, id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteProduct(@PathVariable String id) {
        return this.productService.deleteProduct(id);
    }
}
