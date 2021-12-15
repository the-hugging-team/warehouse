package com.the.hugging.team.services;

import com.the.hugging.team.entities.CashRegister;
import com.the.hugging.team.repositories.CashRegisterRepository;

import java.util.List;

public class CashRegisterService {

    private static CashRegisterService INSTANCE = null;
    private final CashRegisterRepository cashRegisterRepository = CashRegisterRepository.getInstance();

    public static CashRegisterService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CashRegisterService();
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
