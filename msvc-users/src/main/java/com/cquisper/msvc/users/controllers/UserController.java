package com.cquisper.msvc.users.controllers;

import com.cquisper.msvc.users.dto.AddressRequest;
import com.cquisper.msvc.users.dto.AuthResponse;
import com.cquisper.msvc.users.dto.UserRequest;
import com.cquisper.msvc.users.dto.UserResponse;
import com.cquisper.msvc.users.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getAllUsers());
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody UserRequest userRequest){
        return this.service.createUser(userRequest);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponse> updateUser(@RequestBody Map<String, Object> fields, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(this.service.updateUser(fields, id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        this.service.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/credentials/{email}")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse findByEmail(@PathVariable String email){
        return this.service.getCredentialsByEmail(email);
    }

    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getUserById(id));
    }

    @GetMapping("/find-by-email/{email}")
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable String email){
        return ResponseEntity.status(HttpStatus.OK).body(this.service.findByEmail(email));
    }

    @PutMapping("/save-address")
    public ResponseEntity<UserResponse> saveAddress(@RequestBody AddressRequest addressRequest){
        return ResponseEntity.status(HttpStatus.OK).body(this.service.saveAddress(addressRequest));
    }
}
