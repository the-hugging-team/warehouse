package com.the.hugging.team.warehouse;

import com.the.hugging.team.entities.CashRegister;
import com.the.hugging.team.services.CashRegisterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CashRegisterServiceTest {

    private static CashRegister cashRegister;
    private final CashRegisterService cashRegisterService = CashRegisterService.getInstance();

    @Test
    @DisplayName("Should add new cash register")
    void shouldAddNewCashRegister() {
        cashRegister = cashRegisterService.addCashRegister();

        Assertions.assertTrue(cashRegisterService.getAllCashRegisters().contains(cashRegister));
    }

    @Test
    @DisplayName("Should delete cash register")
    void shouldDeleteCashRegister() {
        cashRegisterService.deleteCashRegister(cashRegister);

        Assertions.assertFalse(cashRegisterService.getAllCashRegisters().contains(cashRegister));
    }

}
