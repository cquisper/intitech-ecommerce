package com.cquisper.msvc.auth.client;

import com.cquisper.msvc.auth.dto.AuthResponse;
import com.cquisper.msvc.auth.dto.RegisterRequest;
import com.cquisper.msvc.auth.dto.UserAuthenticate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "msvc-users")
public interface UserFeignClient {

    @PostMapping("/create")
    AuthResponse createUser(@RequestBody RegisterRequest registerRequest);

    @GetMapping("/credentials/{email}")
    UserAuthenticate getCredentialsByEmail(@PathVariable String email);

    @PutMapping("/update/{id}")
    AuthResponse updateUser(@RequestBody Map<String, Object> fields, @PathVariable Long id);
}
