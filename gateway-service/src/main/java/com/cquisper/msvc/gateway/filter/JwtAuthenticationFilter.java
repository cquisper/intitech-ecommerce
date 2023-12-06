package com.cquisper.msvc.gateway.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
@RequiredArgsConstructor @Slf4j
public class JwtAuthenticationFilter implements WebFilter {

    private final ReactiveAuthenticationManager authenticationManager;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return Mono.justOrEmpty(exchange
                        .getRequest()
                        .getHeaders()
                        .getFirst(HttpHeaders.AUTHORIZATION))
                .doOnNext(authHeader -> log.info("authHeader: " + authHeader))
                .filter(this::requiresValidate)
                .switchIfEmpty(chain.filter(exchange).then(Mono.empty()))
                .map(authHeader -> authHeader.replace("Bearer ", ""))
                .flatMap(token -> this.authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(null, token)))
                .onErrorResume(ex-> {
                    log.error("Token invalid");
                    return chain.filter(exchange).then(Mono.empty());
                })
                .flatMap(authentication -> chain
                        .filter(exchange)
                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication)));
    }

    public boolean requiresValidate(String header) throws BadCredentialsException {
        if (Objects.isNull(header)) {
            return false;
        }
        header = header.trim();
        if (!StringUtils.startsWithIgnoreCase(header, "Bearer ")) {
            return false;
        }
        if (header.equalsIgnoreCase("Bearer ")) {
            throw new BadCredentialsException("Token of authentication bearer empty");
        }
        return true;
    }
}
