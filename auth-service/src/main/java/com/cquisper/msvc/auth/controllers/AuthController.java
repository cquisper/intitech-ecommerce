package com.cquisper.msvc.auth.controllers;

import com.cquisper.msvc.auth.dto.AuthRequest;
import com.cquisper.msvc.auth.dto.AuthResponse;
import com.cquisper.msvc.auth.dto.ClaimsResponse;
import com.cquisper.msvc.auth.dto.RegisterRequest;
import com.cquisper.msvc.auth.services.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.authService.register(registerRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest authRequest, HttpServletResponse res){
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.authService.authenticate(authRequest, res));
    }

    @PostMapping("/authenticate/admin")
    public ResponseEntity<AuthResponse> authenticateAdmin(@RequestBody AuthRequest authRequest, HttpServletResponse res){
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.authService.authenticateAdmin(authRequest, res));
    }

    @GetMapping("/claims")
    public ResponseEntity<ClaimsResponse> extractClaims(@RequestParam String token) {
        return ResponseEntity.ok().body(this.authService.extractClaims(token));
    }
}
