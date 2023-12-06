package com.cquisper.msvc.blog.client;

import com.cquisper.msvc.blog.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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

    public Mono<User> getUserById(Long id){
        return this.userWebClient.build().get()
                .uri("http://msvc-users/find-by-id/{id}", id)
                .retrieve()
                .bodyToMono(User.class);
    }
}
