package com.the.hugging.team.warehouse;

import com.the.hugging.team.entities.CashRegister;
import com.the.hugging.team.entities.User;
import com.the.hugging.team.repositories.CashRegisterRepository;
import com.the.hugging.team.services.CashRegisterService;
import com.the.hugging.team.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CashRegisterServiceTest {

    private CashRegisterService cashRegisterService = CashRegisterService.getInstance();
    private static User user;
    @BeforeAll
    public static void setUp(){
       user = UserService.getInstance().getUser(2);
    }
    //TODO doesnt work
    @Test
    @DisplayName("Should add new cash register")
    void shouldAddNewCashRegister(){
        CashRegister cashRegister = cashRegisterService.addCashRegister();

        Assertions.assertTrue(cashRegisterService.getAllCashRegisters().contains(cashRegister));
    }
    //TODO doesnt work
    @Test
    @DisplayName("Should delete cash register")
    void shouldDeleteCashRegister(){
        CashRegister cashRegister = CashRegisterRepository.getInstance().getById(1).orElse(null);
        cashRegisterService.deleteCashRegister(cashRegister);
        Assertions.assertFalse(cashRegisterService.getAllCashRegisters().contains(cashRegister));
    }
}
