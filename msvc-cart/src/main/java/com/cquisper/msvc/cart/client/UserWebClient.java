package com.cquisper.msvc.cart.client;

import com.cquisper.msvc.cart.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserWebClient {

    private final WebClient.Builder userWebClient;

    public Optional<User> getUserByEmail(String email){
        return this.userWebClient.build().get()
                .uri("http://msvc-users/find-by-email/{email}", email)
                .retrieve()
                .bodyToMono(User.class)
                .blockOptional();
    }

    public Optional<User> getUserById(Long id){
        return this.userWebClient.build().get()
                .uri("http://msvc-users/find-by-id/{id}", id)
                .retrieve()
                .bodyToMono(User.class)
                .blockOptional();
    }
}
