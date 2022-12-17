package com.app.vendingmachine.repository;

import com.app.vendingmachine.entity.Role;
import com.app.vendingmachine.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(UserRole roleName);
}