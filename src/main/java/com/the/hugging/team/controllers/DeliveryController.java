package com.the.hugging.team.controllers;

import com.the.hugging.team.utils.WindowHandler;
import com.the.hugging.team.utils.wizard.Wizard;
import com.the.hugging.team.utils.wizard.beans.PaymentBean;
import com.the.hugging.team.utils.wizard.steps.InvoiceStep;
import com.the.hugging.team.utils.wizard.steps.PayStep;
import com.the.hugging.team.utils.wizard.steps.SelectProductStep;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class DeliveryController extends WindowHandler {

    private Wizard wizard;

    @FXML
    private AnchorPane wizardPane;

    @FXML
    private void initialize() {
        InvoiceStep invoiceStep = new InvoiceStep(1, "Invoice", "views/dashboard/wizards/steps/invoice.fxml");
        SelectProductStep selectProductStep = new SelectProductStep(2, "Select product", "views/dashboard/wizards/steps/select-products.fxml");
        PayStep payStep = new PayStep(3, "Finalize payment", "views/dashboard/wizards/steps/pay.fxml");

        PaymentBean.getInstance().setBeanType(PaymentBean.BeanType.DELIVERY);

        Platform.runLater(() -> {
            wizard = new Wizard(wizardPane, List.of(invoiceStep, selectProductStep, payStep), this.getWindow());
            wizard.setUpWizard();
        });
    }
}
