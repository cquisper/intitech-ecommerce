package com.cquisper.msvc.authorization.config;

import com.cquisper.msvc.authorization.client.UserFeignClient;
import com.cquisper.msvc.authorization.dto.UserResponse;
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
            UserResponse userResponse = this.userFeignClient.findByUsername(username);
            return userResponseToUserDetails(userResponse);
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public User userResponseToUserDetails(UserResponse userResponse){
        return new User(userResponse.username(),
                userResponse.password(),
                userResponse.enabled(),
                true, true, true,
                userResponse.roles().stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList()
        );
    }
}
