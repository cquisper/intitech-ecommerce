package com.cquisper.msvc.users.controllers;

import com.cquisper.msvc.users.dto.UserRequest;
import com.cquisper.msvc.users.dto.UserResponse;
import com.cquisper.msvc.users.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> createUser(@RequestBody UserRequest userRequest){
        return this.service.createUser(userRequest);
    }

    @GetMapping("/search/username/{username}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse findByUsername(@PathVariable String username){
        return this.service.findByUsername(username);
    }
}
