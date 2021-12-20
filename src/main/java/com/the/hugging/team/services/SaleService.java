package com.the.hugging.team.services;

import com.the.hugging.team.entities.Sale;
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

    public Sale addSale(Sale sale)
    {
        saleRepository.save(sale);
        return sale;
    }
}
