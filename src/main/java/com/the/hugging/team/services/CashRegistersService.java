package com.the.hugging.team.services;

import com.the.hugging.team.entities.CashRegister;
import com.the.hugging.team.repositories.CashRegisterRepository;

import java.util.List;

public class CashRegistersService {

    private static CashRegistersService INSTANCE = null;
    private final CashRegisterRepository cashRegisterRepository = CashRegisterRepository.getInstance();

    public static CashRegistersService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CashRegistersService();
        }

        return INSTANCE;
    }

    public List<CashRegister> getAllCashRegisters() {
        return cashRegisterRepository.getAll();
    }

    public CashRegister addCashRegister() {
        CashRegister cr = new CashRegister();
        cr.setBalance(0.00);
        cashRegisterRepository.save(cr);
        return cr;
    }

    public void deleteCashRegister(CashRegister cr) {
        cashRegisterRepository.delete(cr);
    }
}
