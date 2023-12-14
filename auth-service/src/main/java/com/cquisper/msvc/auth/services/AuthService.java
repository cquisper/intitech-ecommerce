package com.cquisper.msvc.auth.services;

import com.cquisper.msvc.auth.client.UserFeignClient;
import com.cquisper.msvc.auth.dto.*;
import com.cquisper.msvc.auth.jwt.JwtService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service @Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserFeignClient userClient;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authManager;

    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest registerRequest) {
        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        return Optional.of(this.userClient.createUser(registerRequest))
                .orElseThrow(() -> new RuntimeException("Error created user"));
    }

    public AuthResponse authenticate(AuthRequest authRequest, HttpServletResponse res) throws AuthenticationException {
        this.authManager.authenticate( // Lanza una excepcion si las credenciales son incorrectas
                new UsernamePasswordAuthenticationToken(
                        authRequest.email(),
                        authRequest.password()
                )
        );

        UserAuthenticate userAuthenticate = this.userClient.getCredentialsByEmail(authRequest.email());

        log.info("User {} successfully authenticated", userAuthenticate.email());

        return getAuthResponse(res, userAuthenticate);
    }

    public AuthResponse authenticateAdmin(AuthRequest authRequest, HttpServletResponse res) throws AuthenticationException {
        this.authManager.authenticate( // Lanza una excepcion si las credenciales son incorrectas
                new UsernamePasswordAuthenticationToken(
                        authRequest.email(),
                        authRequest.password()
                )
        );

        UserAuthenticate userAuthenticate = this.userClient.getCredentialsByEmail(authRequest.email());

        if (!userAuthenticate.roles().contains("ROLE_ADMIN")) throw new BadCredentialsException("User is not admin");

        log.info("User admin {} successfully authenticated", userAuthenticate.email());

        return getAuthResponse(res, userAuthenticate);
    }

    private AuthResponse getAuthResponse(HttpServletResponse res, UserAuthenticate userAuthenticate) {
        String jwtToken = this.buildGenerateToken(userAuthenticate);

        String jwtRefreshToken = this.jwtService.generateRefreshToken(userAuthenticate);

        log.info("token generated: {}", jwtToken);

        log.info("refresh token generated: {}", jwtRefreshToken);

        this.userClient.updateUser(Map.of("refreshToken", jwtRefreshToken), userAuthenticate.id());

        Cookie cookie = new Cookie("refreshToken", jwtRefreshToken);

        cookie.setHttpOnly(true);

        cookie.setMaxAge(7 * 60 * 60 * 1000); // 7 Dias

        res.addCookie(cookie);

        return AuthResponse.builder()
                .id(userAuthenticate.id())
                .firstName(userAuthenticate.firstName())
                .lastName(userAuthenticate.lastName())
                .email(userAuthenticate.email())
                .mobile(userAuthenticate.mobile())
                .token(jwtToken)
                .build();
    }

    public ClaimsResponse extractClaims(String token) {
        List<String> authorities;
        try {
            authorities = jwtService.extractAuthorities(token).stream()
                    .map(GrantedAuthority::getAuthority).toList();
        } catch (IOException e) {
            throw new RuntimeException("Can not deserialize authorities");
        }
        return ClaimsResponse.builder()
                .email(this.jwtService.extractUserEmail(token))
                .authorities(authorities)
                .build();
    }

    private String buildGenerateToken(UserAuthenticate userAuthenticate){

        String authoritiesJson;

        try {
            authoritiesJson = new ObjectMapper().writeValueAsString(userAuthenticate.roles());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Can not deserialize authorities");
        }

        return jwtService.generateToken(
                Map.of("authorities", authoritiesJson),
                        userAuthenticate);
    }
}
