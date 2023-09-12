package com.cquisper.msvc.users;

import com.cquisper.msvc.users.enums.RoleName;
import com.cquisper.msvc.users.models.Role;
import com.cquisper.msvc.users.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
@SpringBootApplication
public class MsvcUsersApplication {

    private final RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(MsvcUsersApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadDataTest() {
        return args -> {
            Role roleAdmin = Role.builder()
                    .name(RoleName.ROLE_ADMIN)
                    .build();
            Role roleUser = Role.builder()
                    .name(RoleName.ROLE_USER)
                    .build();
            this.roleRepository.save(roleAdmin);
            this.roleRepository.save(roleUser);
        };
    }
}
