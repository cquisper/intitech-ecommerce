package com.cquisper.msvc.gateway.config;

import com.cquisper.msvc.gateway.client.AuthWebClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Configuration @Slf4j
@RequiredArgsConstructor
public class AppConfig {

    private final AuthWebClient authWebClient;

    @Bean
    public ReactiveAuthenticationManager authenticationManager() {
        return authentication -> Mono.just(authentication.getCredentials().toString())
                .flatMap((token) -> {
                    return this.authWebClient.extractClaims(token)
                            .map(claimsResponse -> {
                                log.info("Claims: {}", claimsResponse);
                                Collection<? extends GrantedAuthority> authorities = claimsResponse.authorities()
                                        .stream()
                                        .map(SimpleGrantedAuthority::new)
                                        .toList();
                                return new UsernamePasswordAuthenticationToken(claimsResponse.email(), null, authorities);
                            })
                            .switchIfEmpty(Mono.error(new AuthenticationCredentialsNotFoundException("Invalid Token")));
                });
    }
}
