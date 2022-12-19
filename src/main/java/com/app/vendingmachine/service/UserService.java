package com.app.vendingmachine.service;

import com.app.vendingmachine.dto.BuyResponse;
import com.app.vendingmachine.dto.DepositDto;
import com.app.vendingmachine.dto.SignupRequest;
import com.app.vendingmachine.entity.Product;
import com.app.vendingmachine.entity.Role;
import com.app.vendingmachine.entity.User;
import com.app.vendingmachine.entity.UserRole;
import com.app.vendingmachine.exception.BadRequestException;
import com.app.vendingmachine.exception.ObjectNotFoundException;
import com.app.vendingmachine.repository.ProductRepository;
import com.app.vendingmachine.repository.RoleRepository;
import com.app.vendingmachine.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.app.vendingmachine.utils.CommonUtils.calculateChange;
import static com.app.vendingmachine.utils.CommonUtils.coinsValidation;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, ProductRepository productRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
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

    public void addDeposit(DepositDto depositDto) throws ObjectNotFoundException, BadRequestException {
        var currentUser = getCurrentUser();
            if (coinsValidation(depositDto.getCoin())) {
                currentUser.setDeposit(currentUser.getDeposit() + depositDto.getCoin());
                userRepository.saveAndFlush(currentUser);
            }
    }

    public void resetDeposit() throws AccessDeniedException, ObjectNotFoundException {
        var currentUser = getCurrentUser();
            currentUser.setDeposit(0L);
            userRepository.saveAndFlush(currentUser);
    }
    public User getCurrentUser() throws ObjectNotFoundException {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return findByUsername(authentication.getName()).orElseThrow(() -> new ObjectNotFoundException("user not found for this :: "));
    }

    public static boolean userHasBuyerRole(User currentUser) {
        return currentUser.getRoles().stream().filter(role -> role.getRoleName().equals(UserRole.BUYER)).count() > 0;
    }


    public BuyResponse buy(Long productId, Long amount) throws ObjectNotFoundException, BadRequestException {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found by Id= " + productId));
        User currentUser = getCurrentUser();
        if (product.getAmountAvailable() < amount)
            throw new BadRequestException("The amount of available product is less than the amount of request.");
        if (currentUser.getDeposit() >= (product.getCost() * amount))
            throw new BadRequestException("The amount of user's deposit is less than the amount of request.");

        product.setAmountAvailable(product.getAmountAvailable() - amount);
        currentUser.setDeposit(currentUser.getDeposit() - (product.getCost() * amount));
        userRepository.save(currentUser);
        return new BuyResponse(product.getCost() * amount, product, calculateChange(currentUser.getDeposit()));
    }
}
