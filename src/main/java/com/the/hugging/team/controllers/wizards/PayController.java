package com.the.hugging.team.controllers.wizards;

import com.the.hugging.team.entities.*;
import com.the.hugging.team.services.ProductService;
import com.the.hugging.team.services.SaleService;
import com.the.hugging.team.services.TransactionService;
import com.the.hugging.team.services.TransactionTypeService;
import com.the.hugging.team.utils.WindowHandler;
import com.the.hugging.team.utils.wizard.beans.SellBean;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.Timestamp;
import java.util.HashSet;

public class PayController extends WindowHandler {
    private final SaleService saleService = SaleService.getInstance();
    private final TransactionService transactionService = TransactionService.getInstance();
    private final TransactionTypeService transactionTypeService = TransactionTypeService.getInstance();
    private final ProductService productService = ProductService.getInstance();

    private final SellBean sellBean = SellBean.getInstance();

    @FXML
    private TextField basePriceField;
    @FXML
    private TextField ddsPercentageField;
    @FXML
    private TextField ddsValueField;
    @FXML
    private TextField finalPriceField;

    private ObservableList<Product> products;
    private Integer dds;
    private Double basePrice;
    private Double ddsValue;
    private Double finalPrice;

    @FXML
    private void initialize() {
        products = sellBean.getProductsData();

        if (sellBean.getBuyerCompany() == null)
            basePrice = products.stream().map(Product::getRetailPrice).reduce(0.00, Double::sum);
        else
            basePrice = products.stream().map(Product::getWholesalePrice).reduce(0.00, Double::sum);

        dds = 20;
        ddsValue = basePrice * dds/100;
        finalPrice = basePrice + ddsValue;

        basePriceField.setText(basePrice.toString());
        if (sellBean.getBuyerCompany() == null)
            ddsPercentageField.setText(dds.toString());
        ddsValueField.setText(ddsValue.toString());
        finalPriceField.setText(finalPrice.toString());

        sellBean.setProductsPrice(basePrice);
        sellBean.setProductsDdsValue(ddsValue);
        sellBean.setProductsFinalPrice(finalPrice);
    }

    @FXML
    public void paymentButtonClick(ActionEvent event) {
        Sale sale = new Sale();
        Transaction transaction = new Transaction();
        Invoice currentInvoice = sellBean.getInvoice();
        CashRegister currentCashRegister = sellBean.getCashRegister();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        User cashier = currentCashRegister.getUser();

        if (currentInvoice != null)
        {
            currentInvoice.setTotalPrice(finalPrice);
            sale.setInvoiceId(currentInvoice.getId()); //?
        }
        else
        {
            // set invoice to null
        }
        sale.setCashRegister(currentCashRegister);
        sale.setCreatedAt(now);
        sale.setCreatedBy(cashier);
        sale.setProducts(new HashSet<>(sellBean.getProductsData()));

        transaction.setCashRegister(sellBean.getCashRegister());
        transaction.setTransactionType(transactionTypeService.getTransactionTypeBySlug("transaction_types.sell"));
        transaction.setAmount(finalPrice);
        transaction.setCreatedAt(now);
        transaction.setCreatedBy(cashier);

        // transaction_id in sale table?
        // add sales view for admin?

        saleService.addSale(sale);
        transactionService.addTransaction(transaction);

        // decrease product quantities in db
    }
}
