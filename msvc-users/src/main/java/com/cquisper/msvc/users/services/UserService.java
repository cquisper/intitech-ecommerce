package com.cquisper.msvc.users.services;

import com.cquisper.msvc.users.dto.UserRequest;
import com.cquisper.msvc.users.enums.RoleName;
import com.cquisper.msvc.users.models.Role;
import com.cquisper.msvc.users.models.User;
import com.cquisper.msvc.users.dto.UserResponse;
import com.cquisper.msvc.users.repositories.RoleRepository;
import com.cquisper.msvc.users.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Transactional
    public Map<String, Object> createUser(UserRequest userRequest){
        Set<Role> roles = this.roleRepository.findByNameIn(userRequest.roles().stream()
                .map(RoleName::valueOf)
                .toList());

        log.info("roles: {}", roles);

        User user = User.builder()
                .username(userRequest.username())
                .password(userRequest.password())
                .enabled(true)
                .photo(userRequest.photo())
                .roles(roles)
                .build();

        log.info("user: {}", user);

        this.userRepository.save(user);

        return Map.of("message", String.format("user [%s] created successfully", user.getUsername()));
    }

    @Transactional(readOnly = true)
    public UserResponse findByUsername(String username){
        return this.userRepository.findByUsername(username)
                .map(user -> UserResponse.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .enabled(user.getEnabled())
                        .roles(user.getRoles().stream().map(role -> role.getName().name()).toList())
                        .build()
                )
                .orElseThrow(() -> new RuntimeException("user not found"));
    }
}