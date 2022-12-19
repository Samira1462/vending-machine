package com.app.vendingmachine.userService;

import com.app.vendingmachine.entity.User;
import com.app.vendingmachine.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;


@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void whenGetAllUser_returnArray() {
        List<User> allUser = userService.getAllUser();
        assertNotNull(allUser);
    }

    @Test
    public void whenGetUser_returnOneUser() {

    }

    @Test
    public void whenGetUser_return() {

    }

    @Test
    public void whenAddUser_returnSuccessfulMessage() {

    }

    @Test
    public void whenAddUserWithTheEmptyRole_returnException() {

    }
    @Test
    public void whenAddDepositWithWrongValue_returnException() {

    }

    @Test
    public void whenResetDeposit_returnDepositValueWithAmountZero() {

    }

}
