package com.cquisper.msvc.users.dto;

import com.cquisper.msvc.users.enums.RoleName;

import java.util.List;

public record UserRequest(
        String username,

        String password,

        String photo,

        List<RoleName> roles
) {
}
