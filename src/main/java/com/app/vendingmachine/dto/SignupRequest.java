package com.app.vendingmachine.dto;

import com.app.vendingmachine.entity.UserRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignupRequest {
    @NonNull
    @NotBlank(message = "{username.notblank}")
    private String username;

    @NonNull
    @NotBlank(message = "{password.notblank}")
    private String password;

    @NonNull
    private Set<UserRole> role;
}
