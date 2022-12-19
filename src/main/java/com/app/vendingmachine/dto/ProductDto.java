package com.app.vendingmachine.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductDto {

    private Long amountAvailable;

    private Long cost;
    private String productName;

    private String seller;

}
