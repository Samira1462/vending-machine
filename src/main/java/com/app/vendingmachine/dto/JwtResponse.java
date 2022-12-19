package com.app.vendingmachine.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Setter
@Getter
public class JwtResponse {

    private String token;

    public JwtResponse() {
    }

}
