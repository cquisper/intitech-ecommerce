package com.cquisper.msvc.products.controllers;

import com.cquisper.msvc.products.dto.ProductRequest;
import com.cquisper.msvc.products.dto.ProductResponse;
import com.cquisper.msvc.products.dto.UserResponse;
import com.cquisper.msvc.products.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Map;

@RestController @Slf4j
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    public Flux<ProductResponse> getAllProducts(@RequestParam Map<String, String> params) {
        return this.productService.getAllProducts(params);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Flux<ProductResponse> getAllProductsFilter(@RequestParam Map<String, String> params) {
        params.forEach((k, v) -> System.out.println(k + ":" + v));
        return this.productService.getAllProductsFilter(params);
    }

    @GetMapping("/find-by-id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ProductResponse> getProductById(@PathVariable String id) {
        return this.productService.getProductById(id);
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

    @PostMapping("/wishlist/{idProduct}")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserResponse> addProductToWishlist(@PathVariable String idProduct, @RequestHeader("X-Email") String email) {
        return this.productService.addProductToWishlist(email, idProduct);
    }

    @GetMapping("/wishlist")
    @ResponseStatus(HttpStatus.OK)
    public Mono<UserResponse> getWishlist(@RequestHeader("X-Email") String email) {
        return this.productService.getWishListUser(email);
    }
}
