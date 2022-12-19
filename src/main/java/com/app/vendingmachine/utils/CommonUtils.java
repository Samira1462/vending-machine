package com.app.vendingmachine.utils;


import com.app.vendingmachine.exception.BadRequestException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CommonUtils {
    private static final Set<Long> coins = new HashSet<>(Arrays.asList(5L,10L,20L,50L,100L));

    public static boolean costValidation(Long cost) throws BadRequestException {
        if (cost % 5 == 0)
            return true;
        throw new BadRequestException("cost should be multiple of 5");
    }
    public static boolean coinsValidation(Long coin) throws BadRequestException {
        if (coins.contains(coin))
            return true;
        throw new BadRequestException("should be enter 5,10,20,50 or 100 cent coins");
    }
    public static Set<Integer> calculateChange(Long amountChange)
    {
        Set<Integer> changes = new HashSet<>();
        while(amountChange > 0)
        {
            if(amountChange >= 100)
            {
                changes.add(100);
                amountChange -= 100;
            }
            else if(amountChange >= 50)
            {
                changes.add(50);
                amountChange -= 50;
            }
            else if(amountChange >= 20)
            {
                changes.add(20);
                amountChange -= 20;
            }
            else if(amountChange >= 10)
            {
                changes.add(10);
                amountChange -= 10;
            }
            else if(amountChange >= 5)
            {
                changes.add(5);
                amountChange -= 5;
            }
        }
        return changes;
    }
}
