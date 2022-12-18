package com.app.vendingmachine.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotBlank;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Login {
    @NonNull
    @NotBlank(message = "{username.notblank}")
    private String username;

    @NonNull
    @NotBlank(message = "{password.notblank}")
    private String password;

}



