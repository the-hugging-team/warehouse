package com.the.hugging.team.services;

import com.the.hugging.team.entities.*;
import com.the.hugging.team.repositories.ProductRepository;
import com.the.hugging.team.repositories.SaleRepository;
import com.the.hugging.team.utils.Session;
import com.the.hugging.team.utils.wizard.beans.SellBean;
import javafx.collections.ObservableList;

import java.sql.Timestamp;

public class SaleService {
    private static SaleService INSTANCE = null;
    private final SaleRepository saleRepository = SaleRepository.getInstance();
    private final ProductRepository productRepository = ProductRepository.getInstance();

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

    public void addSaleFromBean(SellBean sellBean, Double finalPrice) {
        Sale sale = new Sale();
        Transaction transaction = new Transaction();
        Invoice currentInvoice = sellBean.getInvoice();
        CashRegister currentCashRegister = session.getSelectedCashRegister();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        User cashier = session.getUser();

        if (currentInvoice != null) {
            currentInvoice.setTotalPrice(finalPrice);
        }

        sale.setInvoice(currentInvoice);

        sale.setCashRegister(currentCashRegister);
        sale.setCreatedAt(now);
        sale.setCreatedBy(cashier);

        transaction.setCashRegister(currentCashRegister);
        transaction.setTransactionType(transactionTypeService.getTransactionTypeBySlug(TransactionType.SELL));
        transaction.setAmount(finalPrice);
        transaction.setCreatedAt(now);
        transaction.setCreatedBy(cashier);

        transactionService.addTransaction(transaction);
        sale.setTransaction(transaction);

        addSale(sale);

        attachProductsToSale(sellBean.getProductsData(), sale);
    }

    public void attachProductsToSale(ObservableList<Product> products, Sale sale) {
        for (Product product : products) {
            SaleProduct saleProduct = new SaleProduct();
            saleProduct.setProduct(product);
            saleProduct.setSale(sale);
            saleProduct.setQuantity(product.getQuantity());
            saleProduct.setProductQuantityType(product.getProductQuantityType());

            saleRepository.saveSaleProduct(saleProduct);
        }
    }
}
