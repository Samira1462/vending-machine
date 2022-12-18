package com.app.vendingmachine.controller;

import com.app.vendingmachine.dto.SignupRequest;
import com.app.vendingmachine.dto.UserDto;
import com.app.vendingmachine.entity.User;
import com.app.vendingmachine.exception.ObjectNotFoundException;
import com.app.vendingmachine.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    private ModelMapper mapper;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) throws ObjectNotFoundException {
        return ResponseEntity.ok(userService.getUser(id).stream()
                .map(this::convertToUserDto).findFirst()
                .orElseThrow(() -> new ObjectNotFoundException("user not found for this id :: " + id))
        );
    }

    @GetMapping(path = "/getAll", produces = "application/json")
    public ResponseEntity<List<UserDto>> getAllUser() {
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getAllUser().stream()
                        .map(this::convertToUserDto)
                        .collect(Collectors.toList()));
    }

    @DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseEntity.ok().body("deleted user :: " + id);
    }


    @PostMapping(path = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (userService.findByUsername(signupRequest.getUsername()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Error: Username is already taken!");
        }

        return ResponseEntity.status(HttpStatus.OK).body("User registered successfully:: " +  userService.add(signupRequest).getId());
    }

    private UserDto convertToUserDto(User user){
       return mapper.map(user, UserDto.class);
    }
}
