package com.cquisper.msvc.users.repositories;

import com.cquisper.msvc.users.enums.RoleName;
import com.cquisper.msvc.users.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Set<Role> findByNameIn(List<RoleName> name);
}
