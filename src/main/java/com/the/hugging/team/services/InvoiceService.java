package com.the.hugging.team.services;

import com.the.hugging.team.repositories.InvoiceRepository;

public class InvoiceService {

    private static InvoiceService INSTANCE = null;
    private final InvoiceRepository invoiceRepository = InvoiceRepository.getInstance();

    public static InvoiceService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InvoiceService();
        }

        return INSTANCE;
    }
}
