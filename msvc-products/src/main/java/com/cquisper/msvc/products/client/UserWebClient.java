package com.cquisper.msvc.products.client;

import com.cquisper.msvc.products.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserWebClient {

    private final WebClient.Builder userWebClient;

    public Mono<User> getUserByEmail(String email){
        return this.userWebClient.build().get()
                .uri("http://msvc-users/find-by-email/{email}", email)
                .retrieve()
                .bodyToMono(User.class);
    }

    public Mono<User> updateUser(Map<String, Object> fields, Long id){
        return this.userWebClient.build().put()
                .uri("http://msvc-users/update/{id}", id)
                .bodyValue(fields)
                .retrieve()
                .bodyToMono(User.class);
    }
}
