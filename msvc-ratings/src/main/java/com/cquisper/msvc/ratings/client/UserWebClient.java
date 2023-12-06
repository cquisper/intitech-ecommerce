package com.cquisper.msvc.ratings.client;

import com.cquisper.msvc.ratings.models.User;
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
}
