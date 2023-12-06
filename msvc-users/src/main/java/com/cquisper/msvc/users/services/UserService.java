package com.cquisper.msvc.users.services;

import com.cquisper.msvc.users.dto.AddressRequest;
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
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Transactional
    public UserResponse createUser(UserRequest userRequest){
        Set<Role> roles = this.roleRepository.findByNameIn(userRequest.roles().stream()
                .map(RoleName::valueOf)
                .toList());

        log.info("roles: {}", roles);

        User user = User.builder()
                .firstName(userRequest.firstName())
                .lastName(userRequest.lastName())
                .email(userRequest.email())
                .password(userRequest.password())
                .mobile(userRequest.mobile())
                .enabled(true)
                .roles(roles)
                .build();

        log.info("user {} created successfully", user.getEmail());

        return Optional.of(this.userRepository.save(user))
                .map(UserService::entityToDto)
                .orElseThrow(() -> new RuntimeException("user not found"));
    }

    @Transactional(readOnly = true)
    public AuthResponse getCredentialsByEmail(String username){
        return this.userRepository.findByEmail(username)
                .map(user -> AuthResponse.builder()
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .mobile(user.getMobile())
                        .enabled(user.getEnabled())
                        .roles(user.getRoles().stream().map(role -> role.getName().name()).toList())
                        .build()
                )
                .orElseThrow(() -> new RuntimeException("user not found"));
    }

    @Transactional(readOnly = true)
    public UserResponse findByEmail(String username){
        return this.userRepository.findByEmail(username)
                .map(UserService::entityToDto)
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

    @Transactional
    public UserResponse updateUser(Map<String, Object> fields, Long id){
        return this.userRepository.findById(id)
                .map(user -> {
                    fields.forEach((key, value) -> {
                        Field field = ReflectionUtils.findRequiredField(User.class, key);
                        field.setAccessible(true);
                        log.info("Modification: " + field.getName());
                        ReflectionUtils.setField(field, user, value);
                    });
                    return this.userRepository.save(user);
                })
                .map(UserService::entityToDto)
                .orElseThrow(() -> new RuntimeException("user not found"));
    }

    @Transactional
    public void deleteUser(Long id){
        this.userRepository.findById(id)
                .ifPresentOrElse(user -> {
                    user.getRoles().clear();
                    this.userRepository.save(user);
                    this.userRepository.delete(user);
                    log.info("user with id: {} deleted successfully", id);
                }, () -> {
                    throw new RuntimeException("user not found");
                });
    }

    public UserResponse saveAddress(AddressRequest addressRequest){
        return this.userRepository.findById(addressRequest.id())
                .map(user -> {
                    user.setAddress(addressRequest.address());
                    return this.userRepository.save(user);
                })
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
                .refreshToken(user.getRefreshToken())
                .wishList(user.getWishList())
                .address(user.getAddress())
                .photo(user.getPhoto())
                .build();
    }
}