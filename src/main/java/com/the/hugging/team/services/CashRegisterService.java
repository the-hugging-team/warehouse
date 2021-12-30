package com.the.hugging.team.services;

import com.the.hugging.team.entities.CashRegister;
import com.the.hugging.team.repositories.CashRegisterRepository;

import java.util.List;

public class CashRegisterService {

    private static CashRegisterService INSTANCE = null;
    private final CashRegisterRepository cashRegisterRepository = CashRegisterRepository.getInstance();
    private final NotificationService notificationService = NotificationService.getInstance();

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

    public void addBalanceToCashRegister(CashRegister cashRegister, Double finalPrice) {
        cashRegister.setBalance(cashRegister.getBalance() + finalPrice);
        cashRegisterRepository.update(cashRegister);
    }

    public void subBalanceFromCashRegister(CashRegister cashRegister, Double finalPrice) {
        cashRegister.setBalance(cashRegister.getBalance() - finalPrice);
        cashRegisterRepository.update(cashRegister);

        if (cashRegister.getBalance() <= 3000 && cashRegister.getBalance() != 0) {
            notificationService.sendNotification(notificationService.getNotificationTypeBySlug("notification_types.cash_register_reached_minimum_amount"), cashRegister.getId().toString());
        } else if (cashRegister.getBalance() == 0) {
            notificationService.sendNotification(notificationService.getNotificationTypeBySlug("notification_types.cash_register_out_of_money"), cashRegister.getId().toString());
        }
    }

    public CashRegister getMainCashRegister() {
        return cashRegisterRepository.getById(1).orElse(null);
    }
}
