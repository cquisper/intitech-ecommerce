package com.cquisper.msvc.gateway;

import jakarta.ws.rs.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtGrantedAuthoritiesConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class ResourceServerConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http){
        return http
                .authorizeExchange(authorizeRequest -> {
                    authorizeRequest
                            .pathMatchers(HttpMethod.GET,"/api/products/").permitAll()
                            .pathMatchers(HttpMethod.GET,"/api/auth/**").permitAll()
                            .pathMatchers(HttpMethod.POST,"/api/products/create").hasAnyAuthority( "ROLE_ADMIN")
                            .pathMatchers(HttpMethod.GET,"/api/inventory/").hasAnyAuthority("ROLE_ADMIN")
                            .pathMatchers(HttpMethod.POST,"/api/order/place").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                            .anyExchange()
                            .authenticated();
                })
                .oauth2ResourceServer(resourceServer ->
                        resourceServer.jwt(jwtSpec ->
                                        jwtSpec.jwtDecoder(ReactiveJwtDecoders.fromIssuerLocation("http://localhost:9000")))
                )
                .build();
    }

    @Bean
    public ReactiveJwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
        ReactiveJwtGrantedAuthoritiesConverterAdapter reactiveJwtGrantedAuthoritiesConverterAdapter = new ReactiveJwtGrantedAuthoritiesConverterAdapter(jwtGrantedAuthoritiesConverter);
        ReactiveJwtAuthenticationConverter jwtAuthenticationConverter = new ReactiveJwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(reactiveJwtGrantedAuthoritiesConverterAdapter);
        return jwtAuthenticationConverter;
    }
}