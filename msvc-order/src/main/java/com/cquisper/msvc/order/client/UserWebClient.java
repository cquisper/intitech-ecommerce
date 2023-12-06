package com.cquisper.msvc.order.client;

import com.cquisper.msvc.order.models.User;
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
}
