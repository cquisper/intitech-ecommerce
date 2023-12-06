package com.cquisper.msvc.cart.controllers;

import com.cquisper.msvc.cart.dto.CartItemsRequest;
import com.cquisper.msvc.cart.dto.CartResponse;
import com.cquisper.msvc.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add-product")
    public ResponseEntity<CartResponse> addProductToCart(@RequestBody CartItemsRequest cartItemsRequest,
                                                         @RequestHeader("X-Email") String email){
        return ResponseEntity.ok(this.cartService.addProductToCart(cartItemsRequest, email));
    }

    @DeleteMapping("/remove-product/{cartItemId}")
    public ResponseEntity<CartResponse> removeProductFromCart(@PathVariable Long cartItemId,
                                                              @RequestHeader("X-Email") String email){
        return ResponseEntity.ok(this.cartService.removeProductFromCart(cartItemId, email));
    }

    @PostMapping("/update-product/{cartItemId}")
    public ResponseEntity<CartResponse> updateProductFromCart(@PathVariable Long cartItemId,
                                                              @RequestHeader("X-Email") String email){
        return ResponseEntity.ok(this.cartService.updateProductQuantityFromCart(cartItemId, 10, email));
    }

    @PostMapping("/create")
    public ResponseEntity<CartResponse> createCart(@RequestBody List<CartItemsRequest> cartItemsRequest,
                                                   @RequestHeader("X-Email") String email){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.cartService.createCart(cartItemsRequest, email));
    }

    @GetMapping("/user")
    public ResponseEntity<CartResponse> getUserCart(@RequestHeader("X-Email") String email){
        return ResponseEntity.ok(this.cartService.getUserCart(email));
    }

    @GetMapping("/find-by-oderby/{orderBy}")
    public ResponseEntity<CartResponse> getCartByOrderBy(@PathVariable Long orderBy){
        return ResponseEntity.ok(this.cartService.getCartByOrderBy(orderBy));
    }

    @PostMapping("/applycoupon")
    public ResponseEntity<String> applyCoupon(@RequestBody Map<String, String> body, @RequestHeader("X-Email") String email){
        return ResponseEntity.ok(this.cartService.applyCoupon(body.get("coupon"), email));
    }

    @DeleteMapping("/empty-cart")
    public ResponseEntity<CartResponse> emptyCart(@RequestHeader("X-Email") String email){
        return ResponseEntity.ok(this.cartService.emptyCart(email));
    }
}
