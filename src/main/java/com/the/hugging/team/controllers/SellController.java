package com.the.hugging.team.controllers;

import com.the.hugging.team.entities.CashRegister;
import com.the.hugging.team.services.CashRegisterService;
import com.the.hugging.team.utils.Session;
import com.the.hugging.team.utils.WindowHandler;
import com.the.hugging.team.utils.wizard.Wizard;
import com.the.hugging.team.utils.wizard.beans.PaymentBean;
import com.the.hugging.team.utils.wizard.steps.InvoiceStep;
import com.the.hugging.team.utils.wizard.steps.PayStep;
import com.the.hugging.team.utils.wizard.steps.SelectProductStep;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;

import java.util.List;

public class SellController extends WindowHandler {
    private final Session session = Session.getInstance();
    private final CashRegisterService cashRegisterService = CashRegisterService.getInstance();

    private Wizard wizard;

    @FXML
    private AnchorPane cashRegisterPane;
    @FXML
    private ChoiceBox<CashRegister> cashRegisterSelect;
    @FXML
    private Pane selectCashRegisterPane;

    @FXML
    private AnchorPane wizardPane;

    @FXML
    private void initialize() {
        if (session.getSelectedCashRegister() == null) {
            wizardPane.setVisible(false);
            cashRegisterPane.setVisible(true);
            setupCashRegisterSelect();
        } else {
            wizardPane.setVisible(true);
            cashRegisterPane.setVisible(false);
        }


        SelectProductStep selectProductStep = new SelectProductStep(1, "Select product", "views/dashboard/wizards/steps/select-products.fxml");
        InvoiceStep invoiceStep = new InvoiceStep(2, "Invoice", "views/dashboard/wizards/steps/invoice.fxml");
        PayStep payStep = new PayStep(3, "Finalize payment", "views/dashboard/wizards/steps/pay.fxml");

        PaymentBean.getInstance().setBeanType(PaymentBean.BeanType.SELL);

        Platform.runLater(() -> {
            wizard = new Wizard(wizardPane, List.of(selectProductStep, invoiceStep, payStep), this.getWindow());

            if (session.getSelectedCashRegister() != null) {
                wizard.setUpWizard();
            }
        });
    }

    private void setupCashRegisterSelect() {
        cashRegisterSelect.getItems().addAll(cashRegisterService.getAllUnusedCashRegisters());
        cashRegisterSelect.converterProperty().set(new StringConverter<>() {
            @Override
            public String toString(CashRegister cashRegister) {
                return "Cash register " + cashRegister.getId();
            }

            @Override
            public CashRegister fromString(String s) {
                int id = Integer.parseInt(s.split(" ")[2]);
                return cashRegisterService.getCashRegisterById(id);
            }
        });
        cashRegisterSelect.getSelectionModel().selectFirst();


        cashRegisterPane.widthProperty().addListener(
                (observableValue, oldAnchorWidth, newAnchorWidth) -> selectCashRegisterPane.setLayoutX((newAnchorWidth.doubleValue() / 2) - (selectCashRegisterPane.getPrefWidth() / 2)));
        cashRegisterPane.heightProperty().addListener(
                (observableValue, oldAnchorHeight, newAnchorHeight) -> selectCashRegisterPane.setLayoutY((newAnchorHeight.doubleValue() / 2) - (selectCashRegisterPane.getPrefHeight() / 2)));
    }

    @FXML
    private void selectCashRegister(ActionEvent e) {
        CashRegister selected = cashRegisterSelect.getSelectionModel().getSelectedItem();
        cashRegisterService.setCashRegisterUser(selected, session.getUser());

        session.setSelectedCashRegister(cashRegisterSelect.getSelectionModel().getSelectedItem());

        DashboardTemplate.getInstance().setCashRegister(selected);

        wizard.setUpWizard();
        wizardPane.setVisible(true);
        cashRegisterPane.setVisible(false);
    }
}
