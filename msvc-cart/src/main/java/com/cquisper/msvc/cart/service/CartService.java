package com.cquisper.msvc.cart.service;

import com.cquisper.msvc.cart.client.CouponWebClient;
import com.cquisper.msvc.cart.client.ProductWebClient;
import com.cquisper.msvc.cart.client.UserWebClient;
import com.cquisper.msvc.cart.dto.CartItemsRequest;
import com.cquisper.msvc.cart.dto.CartItemsResponse;
import com.cquisper.msvc.cart.dto.CartResponse;
import com.cquisper.msvc.cart.models.Coupon;
import com.cquisper.msvc.cart.models.Product;
import com.cquisper.msvc.cart.models.User;
import com.cquisper.msvc.cart.models.entity.Cart;
import com.cquisper.msvc.cart.models.entity.CartItems;
import com.cquisper.msvc.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service @Slf4j
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    private final UserWebClient userWebClient;

    private final ProductWebClient productWebClient;

    private final CouponWebClient couponWebClient;

    public CartResponse addProductToCart(CartItemsRequest cartItemsRequest, String email){
        User user = this.userWebClient.getUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = this.cartRepository.findByOrderBy(user.getId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        CartItems cartItem = CartItems.builder()
                .productId(cartItemsRequest.productId())
                .count(cartItemsRequest.count())
                .color(cartItemsRequest.color())
                .build();

        BigDecimal price = this.productWebClient.getProductById(cartItemsRequest.productId())
                .map(Product::getPrice)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        cartItem.setPrice(price);

        cart.getCartItems().add(cartItem);

        log.info("Cart with new product {}", cart);

        return getCartResponse(cart);
    }

    public CartResponse removeProductFromCart(Long cartItemId, String email){
        User user = this.userWebClient.getUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = this.cartRepository.findByOrderBy(user.getId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cart.getCartItems().removeIf(cartItems -> cartItems.getId().equals(cartItemId));

        log.info("Cart remove product {}", cart);

        return getCartResponse(cart);
    }

    public CartResponse updateProductQuantityFromCart(Long cartItemId, Integer quantity, String email){
        User user = this.userWebClient.getUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = this.cartRepository.findByOrderBy(user.getId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cart.getCartItems().stream()
                .filter(cartItems -> cartItems.getId().equals(cartItemId))
                .findFirst()
                .ifPresent(cartItems -> cartItems.setCount(quantity));

        log.info("Cart update product {}", cart);

        return getCartResponse(cart);
    }

    private CartResponse getCartResponse(Cart cart) {
        BigDecimal cartTotal = cart.getCartItems().stream()
                .map(cartItems -> cartItems.getPrice().multiply(BigDecimal.valueOf(cartItems.getCount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setCartTotal(cartTotal);

        this.cartRepository.save(cart);

        return entityToDto(cart);
    }

    public CartResponse createCart(List<CartItemsRequest> cartItemsRequest, String email) {
        log.info("Email {}", email);
        User orderByCart = this.userWebClient.getUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        this.cartRepository
                .findByOrderBy(orderByCart.getId())
                .ifPresent(cart -> {
                    log.info("Deleting cart with id: {}", cart.getId());
                    this.cartRepository.delete(cart);
                });

        List<CartItems> cartItemsList = new LinkedList<>();

        cartItemsRequest.forEach(cartItems -> {
            CartItems cartItem = CartItems.builder()
                    .productId(cartItems.productId())
                    .count(cartItems.count())
                    .color(cartItems.color())
                    .build();

            BigDecimal price = this.productWebClient.getProductById(cartItems.productId())
                    .map(Product::getPrice)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            cartItem.setPrice(price);
            cartItemsList.add(cartItem);
        });

        BigDecimal cartTotal = cartItemsList.stream()
                .map(cartItem -> cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getCount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Cart cart = Cart.builder()
                .cartItems(cartItemsList)
                .cartTotal(cartTotal)
                .orderBy(orderByCart.getId())
                .createAt(LocalDateTime.now())
                .build();

        log.info("Saving cart: {}", cart);

        return entityToDto(this.cartRepository.save(cart));
    }

    public CartResponse getUserCart(String email){
        return this.userWebClient.getUserByEmail(email)
                .map(user -> this.cartRepository.findByOrderBy(user.getId())
                        .orElseThrow(() -> new RuntimeException("Cart not found")))
                .map(this::entityToDto)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public CartResponse emptyCart(String email){
        User user = this.userWebClient.getUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = this.cartRepository.findByOrderBy(user.getId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        this.cartRepository.delete(cart);

        log.info("Cart delete {}", cart);

        return entityToDto(cart);
    }

    public CartResponse getCartByOrderBy(Long orderBy){
        return this.cartRepository.findByOrderBy(orderBy)
                .map(this::entityToDto)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    public String applyCoupon(String couponCode, String email){
        Coupon coupon = this.couponWebClient.getCouponByCode(couponCode)
                .orElseThrow(() -> new RuntimeException("Invalid Coupon"));

        User user = this.userWebClient.getUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = this.cartRepository.findByOrderBy(user.getId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        BigDecimal totalAfterDiscount = cart.getCartTotal().subtract(
                cart.getCartTotal().multiply(coupon.getDiscount().divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP))
        ).setScale(2, RoundingMode.HALF_UP);

        cart.setTotalAfterDiscount(totalAfterDiscount);

        this.cartRepository.save(cart);

        log.info("Cart with discount {}", cart);

        return totalAfterDiscount.toString();
    }

    private CartResponse entityToDto(Cart cart){
        return CartResponse.builder()
                .id(cart.getId())
                .products(cart.getCartItems().stream().map(this::entityToDto).toList())
                .cartTotal(cart.getCartTotal())
                .totalAfterDiscount(cart.getTotalAfterDiscount())
                .orderBy(this.userWebClient.getUserById(cart.getOrderBy())
                        .map(user -> User.builder()
                                .id(user.getId())
                                .firstName(user.getFirstName())
                                .lastName(user.getLastName())
                                .build())
                        .orElseThrow(() -> new RuntimeException("User not found"))
                )
                .createAt(cart.getCreateAt())
                .build();
    }

    private CartItemsResponse entityToDto(CartItems cartItems){
        Product product = this.productWebClient.getProductById(cartItems.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return CartItemsResponse.builder()
                .id(cartItems.getId())
                .productId(product.getId())
                .name(product.getName())
                .category(product.getCategory())
                .images(product.getImages())
                .count(cartItems.getCount())
                .color(cartItems.getColor())
                .price(cartItems.getPrice())
                .build();
    }
}
