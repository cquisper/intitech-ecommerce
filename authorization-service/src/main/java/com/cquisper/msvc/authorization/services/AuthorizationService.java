package com.cquisper.msvc.authorization.services;

import com.cquisper.msvc.authorization.client.UserFeignClient;
import com.cquisper.msvc.authorization.dto.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final UserFeignClient userClient;

    private final PasswordEncoder passwordEncoder;

    public Map<String, Object> registerUser(UserRequest userRequest){

        //Send to user-service encrypted password
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        return this.userClient.createUser(userRequest);
    }
}
