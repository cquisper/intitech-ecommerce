package com.cquisper.msvc.gateway.client;

import com.cquisper.msvc.gateway.dto.ClaimsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthWebClient {

    private final WebClient.Builder authWebClient;

    public Mono<ClaimsResponse> extractClaims(String token) {
        return this.authWebClient.baseUrl("http://auth-service")
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/claims")
                        .queryParam("token", token)
                        .build())
                .retrieve()
                .bodyToMono(ClaimsResponse.class);
    }
}
