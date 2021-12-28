package com.the.hugging.team.services;

import com.the.hugging.team.entities.Address;
import com.the.hugging.team.entities.Invoice;
import com.the.hugging.team.repositories.AddressRepository;
import com.the.hugging.team.repositories.InvoiceRepository;

public class InvoiceService {

    private static InvoiceService INSTANCE = null;
    private final InvoiceRepository invoiceRepository = InvoiceRepository.getInstance();
    private final AddressRepository addressRepository = AddressRepository.getInstance();

    public static InvoiceService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InvoiceService();
        }

        return INSTANCE;
    }

    public void save(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    public Address getAddress(String address) {
        Address backupAddress = new Address();
        backupAddress.setAddress(address);
        return addressRepository.getByAddress(address).orElse(backupAddress);
    }
}
