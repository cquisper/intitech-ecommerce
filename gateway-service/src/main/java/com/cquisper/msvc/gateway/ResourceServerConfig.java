package com.cquisper.msvc.gateway;

import jakarta.ws.rs.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class ResourceServerConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http){
        return http
                .authorizeExchange(authorizeRequest -> {
                    authorizeRequest
                            .pathMatchers(HttpMethod.GET,"/api/products").permitAll()
                            .pathMatchers(HttpMethod.POST,"/api/products").hasAnyAuthority("USER", "ADMIN")
                            .pathMatchers("/api/inventory/all").hasAnyAuthority("USER", "ADMIN")
                            .pathMatchers(HttpMethod.POST,"/api/order").hasAnyAuthority("USER", "ADMIN")
                            .anyExchange()
                            .authenticated();
                })
                .oauth2ResourceServer(resourceServer ->
                        resourceServer.jwt(jwtSpec -> jwtSpec.jwkSetUri("http://localhost:9000/oauth2/jwks")))
                .build();
    }
}