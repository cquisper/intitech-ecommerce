package com.cquisper.msvc.authorization.controllers;

import com.cquisper.msvc.authorization.dto.UserRequest;
import com.cquisper.msvc.authorization.services.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthorizationController {

    private final AuthorizationService service;
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> registerUser(@RequestBody UserRequest userRequest){
        return this.service.registerUser(userRequest);
    }
}
