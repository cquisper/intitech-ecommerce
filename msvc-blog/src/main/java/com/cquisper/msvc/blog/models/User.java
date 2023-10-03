package com.cquisper.msvc.blog.models;

import lombok.Builder;
import lombok.Data;

import java.beans.ConstructorProperties;
import java.util.Objects;

@Builder @Data
public class User {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String photo;

    @ConstructorProperties({"id", "firstName", "lastName", "email", "photo"})
    public User(Long id, String firstName, String lastName, String email, String photo) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}
