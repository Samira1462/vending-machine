package com.app.vendingmachine.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DepositDto {
    private String username;
    @NonNull
//    @NotBlank(message = "{coin.notblank}")
    private Long coin;
}
