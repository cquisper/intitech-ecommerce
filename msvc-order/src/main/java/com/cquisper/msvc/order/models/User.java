package com.cquisper.msvc.order.models;

import lombok.Builder;
import lombok.Data;

import java.beans.ConstructorProperties;

@Builder @Data
public class User {
    private Long id;

    private String firstName;

    private String lastName;

    @ConstructorProperties({"id", "firstName", "lastName"})
    public User(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
