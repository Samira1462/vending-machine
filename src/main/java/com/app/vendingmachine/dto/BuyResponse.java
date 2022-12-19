package com.app.vendingmachine.dto;

import com.app.vendingmachine.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;


@AllArgsConstructor
@Data
public class BuyResponse {

    Long spent;
    Product product;
    Set<Integer> changes;

}
