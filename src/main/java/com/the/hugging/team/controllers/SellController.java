package com.the.hugging.team.controllers;

import com.the.hugging.team.utils.WindowHandler;
import com.the.hugging.team.utils.wizard.Wizard;
import com.the.hugging.team.utils.wizard.steps.PayStep;
import com.the.hugging.team.utils.wizard.steps.SelectProductStep;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class SellController extends WindowHandler {

    private Wizard wizard;

    @FXML
    private AnchorPane wizardPane;

    @FXML
    private void initialize() {
        SelectProductStep selectProductStep = new SelectProductStep(1, "Select Product", "views/dashboard/wizards/steps/select-products.fxml");
        PayStep payStep = new PayStep(2, "Finalize payment", "views/dashboard/wizards/steps/pay.fxml");

        Platform.runLater(() -> {
            wizard = new Wizard(wizardPane, List.of(selectProductStep, payStep), this.getWindow());
            wizard.setUpWizard();
        });
    }
}
