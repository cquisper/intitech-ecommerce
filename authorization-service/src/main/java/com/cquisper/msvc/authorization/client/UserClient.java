package com.cquisper.msvc.authorization.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(name = "msvc-users")
public interface UserClient {

    @PostMapping("/create")
    Map<String, Object> createUser(Map<String, Object> userRequest);
}
