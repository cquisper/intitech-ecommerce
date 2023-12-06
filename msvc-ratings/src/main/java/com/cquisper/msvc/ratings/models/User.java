package com.cquisper.msvc.ratings.models;

import lombok.Builder;
import lombok.Data;

import java.beans.ConstructorProperties;
import java.util.Set;

@Builder @Data
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<String> wishList;

    @ConstructorProperties({"id", "firstName", "lastName", "email", "wishList"})
    public User(Long id, String firstName, String lastName, String email, Set<String> wishList) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.wishList = wishList;
    }
}
