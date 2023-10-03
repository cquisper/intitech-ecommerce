package com.cquisper.msvc.authorization.client;

import com.cquisper.msvc.authorization.dto.UserRequest;
import com.cquisper.msvc.authorization.dto.AuthResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "msvc-users")
public interface UserFeignClient {

    @PostMapping("/create")
    Map<String, Object> createUser(@RequestBody UserRequest userRequest);

    @GetMapping("/find-by-email/{email}")
    AuthResponse findByEmail(@PathVariable String email);
}
