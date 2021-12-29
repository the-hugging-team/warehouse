package com.the.hugging.team.services;

import com.the.hugging.team.entities.*;
import com.the.hugging.team.repositories.CashRegisterRepository;
import com.the.hugging.team.repositories.SaleRepository;
import com.the.hugging.team.utils.Session;
import com.the.hugging.team.utils.wizard.beans.PaymentBean;
import javafx.collections.ObservableList;

import java.util.List;

public class SaleService {
    private static SaleService INSTANCE = null;
    private final SaleRepository saleRepository = SaleRepository.getInstance();
    private final CashRegisterRepository cashRegisterRepository = CashRegisterRepository.getInstance();

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

    public void addSaleFromBean(PaymentBean paymentBean, Double finalPrice) {
        Sale sale = new Sale();
        Transaction transaction = new Transaction();
        Invoice currentInvoice = paymentBean.getInvoice();
        CashRegister currentCashRegister = session.getSelectedCashRegister();

        sale.setInvoice(currentInvoice);

        sale.setCashRegister(currentCashRegister);

        transaction.setCashRegister(currentCashRegister);
        transaction.setTransactionType(transactionTypeService.getTransactionTypeBySlug(TransactionType.SELL));
        transaction.setAmount(finalPrice);

        sale.setTransaction(transaction);

        addSale(sale);

        attachProductsToSale(paymentBean.getProductsData(), sale);

        updateCashRegister(currentCashRegister, finalPrice);
    }

    private void attachProductsToSale(ObservableList<Product> products, Sale sale) {
        for (Product product : products) {
            SaleProduct saleProduct = new SaleProduct();
            saleProduct.setProduct(product);
            saleProduct.setSale(sale);
            saleProduct.setQuantity(product.getQuantity());
            saleProduct.setProductQuantityType(product.getProductQuantityType());

            saleRepository.saveSaleProduct(saleProduct);
        }
    }

    private void updateCashRegister(CashRegister cashRegister, Double finalPrice) {
        cashRegister.setBalance(cashRegister.getBalance() + finalPrice);
        cashRegisterRepository.update(cashRegister);
    }

    public List<Sale> getSalesByCashRegister(CashRegister cr) {
        return saleRepository.getByCashRegister(cr);
    }
}
