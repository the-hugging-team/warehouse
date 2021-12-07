package com.the.hugging.team.services;

import com.the.hugging.team.entities.CashRegister;
import com.the.hugging.team.repositories.CashRegisterRepository;

public class CashRegistersService {

    private static CashRegistersService INSTANCE = null;
    private final CashRegisterRepository cashRegisterRepository = CashRegisterRepository.getInstance();

    public static CashRegistersService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CashRegistersService();
        }

        return INSTANCE;
    }

    public CashRegister addCR() {
        CashRegister cr = new CashRegister();
        cr.setBalance(0.00);
        cashRegisterRepository.save(cr);
        return cr;
    }

    public void deleteCR(CashRegister cr) {
        cashRegisterRepository.delete(cr);
    }
}
