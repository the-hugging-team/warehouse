package com.the.hugging.team.services;

import com.the.hugging.team.entities.TransactionType;
import com.the.hugging.team.repositories.TransactionTypeRepository;

public class TransactionTypeService {

    private static final TransactionTypeRepository transactionTypeRepository = TransactionTypeRepository.getInstance();
    private static TransactionTypeService INSTANCE = null;

    public static TransactionTypeService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TransactionTypeService();
        }

        return INSTANCE;
    }

    public TransactionType getTransactionTypeBySlug(String slug) {
        return transactionTypeRepository.getBySlug(slug).orElse(null);
    }
}
