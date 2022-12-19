package com.app.vendingmachine;

import com.app.vendingmachine.exception.BadRequestException;
import com.app.vendingmachine.utils.CommonUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static com.app.vendingmachine.utils.CommonUtils.coinsValidation;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CommonUtilsTest {


    @Test
    public void CALCULATE_CHANGE_TEST() {
        Long deposit = 125L;
       Set<Integer> coins = CommonUtils.calculateChange(deposit);

        Assertions.assertEquals(3 ,coins.size() );
        Assertions.assertTrue(coins.contains(100));
        Assertions.assertTrue(coins.contains(20));
        Assertions.assertTrue(coins.contains(5));
        Assertions.assertFalse(coins.contains(50));
    }

    @Test
    public void COST_VALIDATION_TEST() {
        Long cost = 132L;
        var result = assertThrows(BadRequestException.class, () -> coinsValidation(cost));
        assertEquals("should be enter 5,10,20,50 or 100 cent coins", result.getMessage());
    }
    @Test
    public void COINS_VALIDATION_TEST() throws BadRequestException {
        Long cost = 20L;
        Assertions.assertTrue(coinsValidation(cost));
    }
}
