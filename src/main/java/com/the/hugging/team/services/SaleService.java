package com.the.hugging.team.services;

import com.the.hugging.team.repositories.SaleRepository;

public class SaleService {
    private static SaleService INSTANCE = null;
    private final SaleRepository saleRepository = SaleRepository.getInstance();

    public static SaleService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SaleService();
        }

        return INSTANCE;
    }
}
