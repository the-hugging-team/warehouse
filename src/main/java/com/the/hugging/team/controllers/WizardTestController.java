package com.the.hugging.team.controllers;

import com.the.hugging.team.utils.wizard.Wizard;
import com.the.hugging.team.utils.wizard.WizardStep;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class WizardTestController extends DashboardTemplate {
    private Wizard wizard = null;

    @FXML
    private AnchorPane mainAnchor;

    @FXML
    @Override
    public void initialize() {
        WizardStep step1 = new WizardStep(1, "Step 1 content", "views/dashboard/cruds/users-crud.fxml");
        WizardStep step2 = new WizardStep(2, "Step 2 content", "views/dashboard/cruds/cash-registers-crud.fxml");
        WizardStep step3 = new WizardStep(3, "Step 3 content", "views/dashboard/cruds/users-crud.fxml");


        Platform.runLater(() -> {
            wizard = new Wizard(mainAnchor, List.of(step1, step2, step3), this.getWindow());
            wizard.setUpWizard();
        });
    }

    @FXML
    void create() {

    }

    @FXML
    void edit() {

    }

    @FXML
    void delete() {

    }
}
