package com.cquisper.msvc.users.services;

import com.cquisper.msvc.users.dto.AuthResponse;
import com.cquisper.msvc.users.dto.UserRequest;
import com.cquisper.msvc.users.enums.RoleName;
import com.cquisper.msvc.users.models.Role;
import com.cquisper.msvc.users.models.User;
import com.cquisper.msvc.users.dto.UserResponse;
import com.cquisper.msvc.users.repositories.RoleRepository;
import com.cquisper.msvc.users.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.List;
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
                .firstName(userRequest.firstName())
                .lastName(userRequest.lastName())
                .email(userRequest.email())
                .mobile(userRequest.mobile())
                .password(userRequest.password())
                .enabled(true)
                .roles(roles)
                .build();

        log.info("user: {}", user);

        this.userRepository.save(user);

        return Map.of("message", String.format("user [%s] created successfully", user.getEmail()));
    }

    @Transactional(readOnly = true)
    public AuthResponse findByEmail(String username){
        return this.userRepository.findByEmail(username)
                .map(user -> AuthResponse.builder()
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .enabled(user.getEnabled())
                        .roles(user.getRoles().stream().map(role -> role.getName().name()).toList())
                        .build()
                )
                .orElseThrow(() -> new RuntimeException("user not found"));
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers(){
        return this.userRepository.findAll()
                .stream()
                .map(UserService::entityToDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id){
        return this.userRepository.findById(id)
                .map(UserService::entityToDto)
                .orElseThrow(() -> new RuntimeException("user not found"));
    }

    public static UserResponse entityToDto(User user){
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .mobile(user.getMobile())
                .enabled(user.getEnabled())
                .roles(user.getRoles().stream().map(role -> role.getName().name()).toList())
                .photo(user.getPhoto())
                .build();
    }
}