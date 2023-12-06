package com.cquisper.msvc.auth.config;

import com.cquisper.msvc.auth.client.UserFeignClient;
import com.cquisper.msvc.auth.dto.UserAuthenticate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Configuration @Slf4j
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserFeignClient userClient;

    @Bean
    public UserDetailsService userDetailsService(){
        return username -> Optional.of(userClient.getCredentialsByEmail(username))
                    .map(this::userToUserDetails)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario [%s], no encontrado".formatted(username)));
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public User userToUserDetails(UserAuthenticate userAuthenticate){
        List<SimpleGrantedAuthority> authorities = userAuthenticate.roles()
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList();
        return new User(userAuthenticate.email(),
                userAuthenticate.password(),
                userAuthenticate.enabled(), true, true, true,
                authorities);
    }
}
