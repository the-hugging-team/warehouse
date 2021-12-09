package com.the.hugging.team.services;

import com.the.hugging.team.entities.CashRegister;
import com.the.hugging.team.entities.Transaction;
import com.the.hugging.team.repositories.TransactionRepository;

import java.util.List;

public class TransactionService {

    private static final TransactionRepository transactionRepository = TransactionRepository.getInstance();
    private static TransactionService INSTANCE = null;

    public static TransactionService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TransactionService();
        }

        return INSTANCE;
    }

    public List<Transaction> getTransactionsByCashRegister(CashRegister cr) {
        return transactionRepository.getByCashRegister(cr);
    }
}
