package com.the.hugging.team.controllers.wizards;

import com.the.hugging.team.entities.Invoice;
import com.the.hugging.team.utils.WindowHandler;
import com.the.hugging.team.utils.wizard.beans.SellBean;
import com.the.hugging.team.utils.wizard.events.EventSource;
import com.the.hugging.team.utils.wizard.events.EventType;
import com.the.hugging.team.utils.wizard.events.NextStepEvent;
import com.the.hugging.team.utils.wizard.events.PreviousStepEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class InvoiceController extends WindowHandler {
    private final EventSource eventSource = EventSource.getInstance();
    private final SellBean sellBean = SellBean.getInstance();

    @FXML
    private AnchorPane questionAnchor;
    @FXML
    private Pane questionPane;

    @FXML
    private AnchorPane invoiceAnchor;

    @FXML
    private Pane companyOne;

    @FXML
    private TextField companyOneName;
    @FXML
    private TextField companyOneAddress;
    @FXML
    private TextField companyOneDDSNumber;
    @FXML
    private TextField companyOneEIK;
    @FXML
    private TextField companyOneMOL;

    @FXML
    private Pane companyTwo;

    @FXML
    private TextField companyTwoName;
    @FXML
    private TextField companyTwoAddress;
    @FXML
    private TextField companyTwoDDSNumber;
    @FXML
    private TextField companyTwoEIK;
    @FXML
    private TextField companyTwoMOL;

    @FXML
    private Pane buyer;

    @FXML
    private TextField buyerName;
    @FXML
    private TextField buyerDate;

    @FXML
    private Pane seller;

    @FXML
    private TextField sellerName;

    @FXML
    private void initialize() {
        if (sellBean.getInvoice() == null) {
            questionAnchor.setVisible(true);
            invoiceAnchor.setVisible(false);
        } else {
            questionAnchor.setVisible(false);
            invoiceAnchor.setVisible(true);
        }

        questionAnchor.widthProperty().addListener(
                (observableValue, oldAnchorWidth, newAnchorWidth) -> questionPane.setLayoutX((newAnchorWidth.doubleValue() / 2) - (questionPane.getPrefWidth() / 2)));
        questionAnchor.heightProperty().addListener(
                (observableValue, oldAnchorHeight, newAnchorHeight) -> questionPane.setLayoutY((newAnchorHeight.doubleValue() / 2) - (questionPane.getPrefHeight() / 2)));
    }

    @FXML
    public void nextStep() {
        eventSource.fire(EventType.NEXT_STEP_EVENT_TYPE, new NextStepEvent());
    }

    @FXML
    public void prevStep() {
        eventSource.fire(EventType.PREVIOUS_STEP_EVENT_TYPE, new PreviousStepEvent());
    }

    @FXML
    public void noClick() {
        sellBean.setInvoice(null);
        nextStep();
    }

    @FXML
    public void yesClick() {
        questionAnchor.setVisible(false);
        invoiceAnchor.setVisible(true);
        sellBean.setInvoice(new Invoice());
    }
}
