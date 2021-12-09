package com.the.hugging.team.services;

import com.the.hugging.team.entities.Supplier;
import com.the.hugging.team.repositories.SupplierRepository;

import java.util.List;

public class SupplierService {

    private static SupplierService INSTANCE = null;
    private final SupplierRepository supplierRepository = SupplierRepository.getInstance();

    public static SupplierService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SupplierService();
        }

        return INSTANCE;
    }

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.getAll();
    }

    public void setSupplierName(Supplier supplier, String name) {
        supplier.setName(name);
        supplierRepository.update(supplier);
    }

    public Supplier addSupplier(String name) {
        Supplier supplier = new Supplier();
        supplier.setName(name);
        supplierRepository.save(supplier);
        return supplier;
    }

    public void deleteSupplier(Supplier supplier) {
        supplierRepository.delete(supplier);
    }
}
