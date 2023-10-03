package com.cquisper.msvc.products.models;

import lombok.Builder;
import lombok.Data;

import java.beans.ConstructorProperties;

@Builder @Data
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    @ConstructorProperties({"id", "firstName", "lastName", "email"})
    public User(Long id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
