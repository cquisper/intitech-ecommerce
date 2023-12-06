package com.cquisper.msvc.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Component @Slf4j
public class EmailHeaderFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Principal::getName)
                .doOnNext(email -> {
                    log.info("Email extract context {}", email);
                    exchange.getRequest().mutate().headers(httpHeaders -> {
                        httpHeaders.add("X-Email", email);
                    });
                })
                .then(chain.filter(exchange));
    }

    @Override
    public int getOrder() {
        return 10;
    }
}
