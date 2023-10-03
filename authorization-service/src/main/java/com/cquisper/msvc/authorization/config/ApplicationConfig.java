package com.cquisper.msvc.authorization.config;

import com.cquisper.msvc.authorization.client.UserFeignClient;
import com.cquisper.msvc.authorization.dto.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserFeignClient userFeignClient;

    @Bean
    public UserDetailsService userDetailsService(){
        return username -> {
            AuthResponse authResponse = this.userFeignClient.findByEmail(username);
            return userResponseToUserDetails(authResponse);
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public User userResponseToUserDetails(AuthResponse authResponse){
        return new User(authResponse.email(),
                authResponse.password(),
                authResponse.enabled(),
                true, true, true,
                authResponse.roles().stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList()
        );
    }
}
