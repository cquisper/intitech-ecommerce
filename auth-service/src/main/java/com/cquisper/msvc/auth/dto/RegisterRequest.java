package com.cquisper.msvc.auth.dto;

import lombok.Data;

import java.util.List;

@Data
public class RegisterRequest {
    private String firstName;

    private String lastName;

    private String email;

    private String mobile;

    private String password;

    private List<String> roles;
}
