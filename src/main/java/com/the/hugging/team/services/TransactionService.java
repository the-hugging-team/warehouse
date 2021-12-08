package com.the.hugging.team.services;

import com.the.hugging.team.entities.CashRegister;
import com.the.hugging.team.entities.Transaction;
import com.the.hugging.team.repositories.TransactionRepository;

import java.util.List;

public class TransactionService {
    private static final TransactionRepository transactionRepository = TransactionRepository.getInstance();

    public static List<Transaction> getTransactionsByCashRegister(CashRegister cr) {
        return transactionRepository.getByCashRegister(cr);
    }
}
