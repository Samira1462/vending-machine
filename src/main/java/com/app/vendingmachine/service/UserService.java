package com.app.vendingmachine.service;

import com.app.vendingmachine.dto.SignupRequest;
import com.app.vendingmachine.entity.Role;
import com.app.vendingmachine.entity.User;
import com.app.vendingmachine.entity.UserRole;
import com.app.vendingmachine.repository.RoleRepository;
import com.app.vendingmachine.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public User add(SignupRequest signupRequest) {
        User user = new User(signupRequest.getUsername(), passwordEncoder.encode(signupRequest.getPassword()));

        Set<UserRole> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles.size() == 0) {
            throw new RuntimeException("Error: Role is Empty.");
        }

        strRoles.forEach(role -> {
            switch (role) {
                case SELLER -> {
                    Role adminRole = roleRepository.findByRoleName(UserRole.SELLER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);
                }
                case BUYER -> {
                    Role modRole = roleRepository.findByRoleName(UserRole.BUYER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(modRole);
                }
                default -> log.info("info:set Role is set.");
            }
        });

        user.setRoles(roles);
        return userRepository.save(user);
    }
}
