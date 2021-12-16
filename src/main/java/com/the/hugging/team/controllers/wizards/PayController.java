package com.the.hugging.team.controllers.wizards;

import com.the.hugging.team.services.InvoiceService;
import com.the.hugging.team.services.ProductService;
import com.the.hugging.team.services.TransactionService;
import com.the.hugging.team.utils.WindowHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class PayController extends WindowHandler {
    private final TransactionService transactionService = TransactionService.getInstance();
    private final InvoiceService invoiceService = InvoiceService.getInstance();
    private final ProductService productService = ProductService.getInstance();

    @FXML
    private Button paymentButton;
    @FXML
    private TextField basePrice;
    @FXML
    private TextField ddsPercentage;
    @FXML
    private TextField ddsValue;
    @FXML
    private TextField finalPrice;


    @FXML
    private void initialize() {

    }
}
