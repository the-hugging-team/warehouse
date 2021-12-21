package com.the.hugging.team.services;

import com.the.hugging.team.entities.*;
import com.the.hugging.team.repositories.SaleRepository;
import com.the.hugging.team.utils.Session;
import com.the.hugging.team.utils.wizard.beans.SellBean;

import java.sql.Timestamp;
import java.util.HashSet;

public class SaleService {
    private static SaleService INSTANCE = null;
    private final SaleRepository saleRepository = SaleRepository.getInstance();
    private final TransactionService transactionService = TransactionService.getInstance();
    private final TransactionTypeService transactionTypeService = TransactionTypeService.getInstance();
    private final Session session = Session.getInstance();

    public static SaleService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SaleService();
        }

        return INSTANCE;
    }

    public Sale addSale(Sale sale) {
        saleRepository.save(sale);
        return sale;
    }

    public Sale addSaleByBean(SellBean sellBean, Double finalPrice) {
        Sale sale = new Sale();
        Transaction transaction = new Transaction();
        Invoice currentInvoice = sellBean.getInvoice();
        CashRegister currentCashRegister = session.getSelectedCashRegister();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        User cashier = currentCashRegister.getUser();

        if (currentInvoice != null) {
            currentInvoice.setTotalPrice(finalPrice);
            sale.setInvoice(currentInvoice);
        } else sale.setInvoice(null);

        sale.setCashRegister(currentCashRegister);
        sale.setCreatedAt(now);
        sale.setCreatedBy(cashier);
        sale.setProducts(new HashSet<>(sellBean.getProductsData()));

        transaction.setCashRegister(currentCashRegister);
        transaction.setTransactionType(transactionTypeService.getTransactionTypeBySlug(TransactionType.SELL));
        transaction.setAmount(finalPrice);
        transaction.setCreatedAt(now);
        transaction.setCreatedBy(cashier);

        sale.setTransaction(transaction);
        transactionService.addTransaction(transaction);

        return addSale(sale);
    }
}
