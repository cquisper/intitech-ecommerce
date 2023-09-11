package com.cquisper.msvc.users.services;

import com.cquisper.msvc.users.dto.UserRequest;
import com.cquisper.msvc.users.models.Role;
import com.cquisper.msvc.users.models.User;
import com.cquisper.msvc.users.repositories.RoleRepository;
import com.cquisper.msvc.users.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Map<String, Object> createUser(UserRequest userRequest){
        Set<Role> roles = this.roleRepository.findByNameIn(userRequest.roles());

        log.info("roles: {}", roles);

        User user = User.builder()
                .username(userRequest.username())
                .password(passwordEncoder.encode(userRequest.password()))
                .enabled(true)
                .photo(userRequest.photo())
                .roles(roles)
                .build();

        log.info("user: {}", user);

        this.userRepository.save(user);

        return Map.of("message", String.format("user [%s] created successfully", user.getUsername()));
    }
}