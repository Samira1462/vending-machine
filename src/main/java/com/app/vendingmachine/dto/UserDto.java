package com.app.vendingmachine.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class UserDto {
    private Long id;
    @NotBlank(message = "{username.notblank}")
    private String username;
    private String password;
    private String deposit;
    private String roles;
    private boolean isActive;
}
