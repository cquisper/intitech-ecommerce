package com.cquisper.msvc.gateway.config;

import com.cquisper.msvc.gateway.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import static org.springframework.http.HttpMethod.*;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.util.List;

import static org.springframework.http.HttpHeaders.*;
@Configuration @EnableWebFlux @Slf4j
@EnableWebFluxSecurity @RequiredArgsConstructor
public class ResourceServerConfig implements WebFluxConfigurer {

    private final JwtAuthenticationFilter authenticationFilter;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http){
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                //.cors(corsSpec -> corsSpec.configurationSource(CorsWebFilter.).getConfigurationSource())
                .authorizeExchange(authorizeRequest -> {
                    authorizeRequest
                            .pathMatchers(GET, "/api/products/all", "/api/products/find-by-id/{id}",
                                    "/api/products/test").permitAll()
                            .pathMatchers(POST, "/api/auth/register", "/api/auth/authenticate", "/api/auth/authenticate/admin").permitAll()
                            .pathMatchers(POST, "/api/products/wishlist/{idProduct}").hasAnyAuthority("ROLE_USER")
                            .pathMatchers(GET, "/api/products/wishlist").hasAnyAuthority("ROLE_USER")
                            .pathMatchers(POST,"/api/products/create").hasAnyAuthority( "ROLE_ADMIN")
                            .pathMatchers(PATCH,"/api/products/update/{id}").hasAnyAuthority( "ROLE_ADMIN")
                            .pathMatchers(DELETE,"/api/products/delete").hasAnyAuthority( "ROLE_ADMIN")
                            .pathMatchers(POST,"/api/products/color/create").hasAnyAuthority( "ROLE_ADMIN")
                            .pathMatchers(PATCH, "/api/products/color/update/{id}").hasAnyAuthority( "ROLE_ADMIN")
                            .pathMatchers(DELETE, "/api/products/color/delete/{id}").hasAnyAuthority( "ROLE_ADMIN")
                            .pathMatchers(GET,"/api/products/color/all", "/api/products/color/find-by-id/{id}").permitAll()
                            .pathMatchers(GET,"/api/users/find-by-id/{id}",
                                    "/api/users/find-by-email/{email}").hasAnyAuthority( "ROLE_ADMIN")
                            .pathMatchers(POST, "/api/products/brand/create").hasAnyAuthority( "ROLE_ADMIN")
                            .pathMatchers(PATCH, "/api/products/brand/update/{id}").hasAnyAuthority( "ROLE_ADMIN")
                            .pathMatchers(DELETE, "/api/products/brand/delete/{id}").hasAnyAuthority( "ROLE_ADMIN")
                            .pathMatchers(GET,"/api/products/brand/all", "/api/products/brand/find-by-id/{id}").permitAll()
                            .pathMatchers(POST, "/api/products/category/create").hasAnyAuthority( "ROLE_ADMIN")
                            .pathMatchers(PATCH, "/api/products/category/update/{id}").hasAnyAuthority( "ROLE_ADMIN")
                            .pathMatchers(DELETE, "/api/products/category/delete/{id}").hasAnyAuthority( "ROLE_ADMIN")
                            .pathMatchers(GET,"/api/products/category/all", "/api/products/category/find-by-id/{id}").permitAll()
                            .pathMatchers(GET,"/api/users/all").permitAll()
                            .pathMatchers(PUT, "/api/users/save-address").permitAll()
                            .pathMatchers(POST,"/api/upload/").hasAnyAuthority( "ROLE_ADMIN")
                            .pathMatchers(DELETE,"/api/upload/delete/{id}", "/api/upload/delete-name/{name}").hasAnyAuthority( "ROLE_ADMIN")
                            .pathMatchers(GET,"/api/blog/all").permitAll()
                            .pathMatchers(GET,"/api/blog/find-by-id/{id}").permitAll()
                            .pathMatchers(POST,"/api/blog/create").hasAnyAuthority( "ROLE_ADMIN")
                            .pathMatchers(PATCH,"/api/blog/update/{id}").hasAnyAuthority( "ROLE_ADMIN")
                            .pathMatchers(DELETE,"/api/blog/delete/{id}").hasAnyAuthority( "ROLE_ADMIN")
                            .pathMatchers(PUT,"/api/blog/like/{idBlog}").hasAnyAuthority( "ROLE_USER")
                            .pathMatchers(POST,"/api/blog/comment").hasAnyAuthority( "ROLE_USER")
                            .pathMatchers(POST,"/api/blog/reply").hasAnyAuthority( "ROLE_USER")
                            .pathMatchers(GET,"/api/blog/category/all").permitAll()
                            .pathMatchers(GET,"/api/blog/category/find-by-id/{id}").permitAll()
                            .pathMatchers(POST,"/api/blog/category/create").hasAnyAuthority( "ROLE_ADMIN")
                            .pathMatchers(PATCH,"/api/blog/category/update/{id}").hasAnyAuthority( "ROLE_ADMIN")
                            .pathMatchers(DELETE,"/api/blog/category/delete/{id}").hasAnyAuthority( "ROLE_ADMIN")
                            .pathMatchers(GET,"/api/coupon/all", "/api/coupon/find-by-id/{id}").hasAnyAuthority( "ROLE_ADMIN")
                            .pathMatchers(POST,"/api/coupon/create").hasAnyAuthority( "ROLE_ADMIN")
                            .pathMatchers(PATCH,"/api/coupon/update/{id}").hasAnyAuthority( "ROLE_ADMIN")
                            .pathMatchers(DELETE,"/api/coupon/delete/{id}").hasAnyAuthority( "ROLE_ADMIN")
                            .pathMatchers(GET, "/api/cart/user").hasAnyAuthority("ROLE_USER")
                            .pathMatchers(POST,"/api/cart/create", "/api/cart/add-product","/api/cart/applycoupon", "/api/cart/update-product/{cartItemId}").hasAnyAuthority( "ROLE_USER")
                            .pathMatchers(DELETE, "/api/cart/empty-cart", "/api/cart/remove-product/{cartItemId}").hasAnyAuthority( "ROLE_USER")
                            .pathMatchers(GET, "/api/inventory/all", "/api/inventory/find-by-id/{id}", "/find-by-product-id/{productId}").permitAll()
                            .pathMatchers(POST, "/api/order/cash-order").hasAnyAuthority( "ROLE_USER")
                            .pathMatchers(GET, "/api/order/get-orders").hasAnyAuthority( "ROLE_USER")
                            .pathMatchers(GET, "/api/order/get-all-orders", "/api/order/get-orders-by-user/{id}", "/api/order/find-by-id/{id}").hasAnyAuthority( "ROLE_ADMIN")
                            .pathMatchers(PUT, "/api/order/update-status/{id}").hasAnyAuthority( "ROLE_ADMIN")
                            .pathMatchers(DELETE, "/api/order/delete/{id}").hasAnyAuthority( "ROLE_ADMIN")
                            .pathMatchers(POST, "/api/enquire/create").permitAll()
                            .pathMatchers(PATCH, "/api/enquire/update/{id}").hasAnyAuthority( "ROLE_ADMIN")
                            .pathMatchers(DELETE, "/api/enquire/delete/{id}").hasAnyAuthority( "ROLE_ADMIN")
                            .pathMatchers(GET, "/api/enquire/all", "/api/enquire/find-by-id/{id}").permitAll()
                            .pathMatchers(POST, "/api/payment/checkout").hasAnyAuthority( "ROLE_USER", "ROLE_ADMIN")
                            .pathMatchers(POST, "/api/payment/payment-verification").hasAnyAuthority( "ROLE_USER", "ROLE_ADMIN")
                            .anyExchange()
                            .authenticated();
                })
                .addFilterBefore(authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(List.of("https://s8qbz78w-3000.brs.devtunnels.ms", "*"));
        corsConfig.setMaxAge(20000L);
        //corsConfig.setAllowCredentials(true);
        corsConfig.setAllowedMethods(List.of(GET.name(),
                POST.name(), PUT.name(),
                PATCH.name(), DELETE.name()));
        corsConfig.setAllowedHeaders(List.of(CONTENT_TYPE, AUTHORIZATION));

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }
}