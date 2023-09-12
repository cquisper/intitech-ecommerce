package com.cquisper.msvc.authorization.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserRequest {

    private String username;

    private String password;

    private String photo;

    private List<String> roles;
}
