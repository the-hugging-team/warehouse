package com.the.hugging.team.controllers.wizards;

import com.the.hugging.team.entities.Company;
import com.the.hugging.team.entities.Invoice;
import com.the.hugging.team.services.CompanyService;
import com.the.hugging.team.services.InvoiceService;
import com.the.hugging.team.utils.Session;
import com.the.hugging.team.utils.WindowHandler;
import com.the.hugging.team.utils.wizard.beans.PaymentBean;
import com.the.hugging.team.utils.wizard.events.EventSource;
import com.the.hugging.team.utils.wizard.events.EventType;
import com.the.hugging.team.utils.wizard.events.NextStepEvent;
import com.the.hugging.team.utils.wizard.events.PreviousStepEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class InvoiceController extends WindowHandler {
    private final EventSource eventSource = EventSource.getInstance();
    private final PaymentBean paymentBean = PaymentBean.getInstance();
    private final InvoiceService invoiceService = InvoiceService.getInstance();
    private final CompanyService companyService = CompanyService.getInstance();
    private final Session session = Session.getInstance();

    private Invoice invoice;
    private Company companyOne = null, companyTwo = null;

    @FXML
    private AnchorPane questionAnchor;
    @FXML
    private Pane questionPane;

    @FXML
    private AnchorPane invoiceAnchor;

    @FXML
    private Pane companyOnePane;

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
    private Pane companyTwoPane;

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
        if (paymentBean.getBeanType().equals(PaymentBean.BeanType.SELL)) {
            if (paymentBean.getInvoice() == null) {
                questionAnchor.setVisible(true);
                invoiceAnchor.setVisible(false);
            } else {
                questionAnchor.setVisible(false);
                invoiceAnchor.setVisible(true);
                initInvoice();
            }
            lockCompanyTwo();
        } else if (paymentBean.getBeanType().equals(PaymentBean.BeanType.DELIVERY)) {
            questionAnchor.setVisible(false);
            invoiceAnchor.setVisible(true);
            initInvoice();
            lockCompanyOne();
        }

        questionAnchor.widthProperty().addListener(
                (observableValue, oldAnchorWidth, newAnchorWidth) -> questionPane.setLayoutX((newAnchorWidth.doubleValue() / 2) - (questionPane.getPrefWidth() / 2)));
        questionAnchor.heightProperty().addListener(
                (observableValue, oldAnchorHeight, newAnchorHeight) -> questionPane.setLayoutY((newAnchorHeight.doubleValue() / 2) - (questionPane.getPrefHeight() / 2)));
    }

    @FXML
    public void nextStep() {
        eventSource.fire(EventType.NEXT_STEP_EVENT_TYPE, new NextStepEvent());
        saveInvoiceData();
    }

    @FXML
    public void prevStep() {
        eventSource.fire(EventType.PREVIOUS_STEP_EVENT_TYPE, new PreviousStepEvent());
        saveInvoiceData();
    }

    @FXML
    public void noClick() {
        nextStep();
    }

    @FXML
    public void yesClick() {
        questionAnchor.setVisible(false);
        invoiceAnchor.setVisible(true);
        initInvoice();
    }

    private void initInvoice() {
        invoice = paymentBean.getInvoice();
        if (invoice == null) {
            invoice = new Invoice();

            invoice.setCreatedAt(new Timestamp(System.currentTimeMillis()));

            if (paymentBean.getBeanType().equals(PaymentBean.BeanType.SELL)) {
                companyOne = new Company();
                companyTwo = companyService.getCellaCompany();
                invoice.setSeller(session.getUser().getFirstName() + " " + session.getUser().getLastName());
            } else if (paymentBean.getBeanType().equals(PaymentBean.BeanType.DELIVERY)) {
                companyOne = companyService.getCellaCompany();
                companyTwo = new Company();
                invoice.setBuyer(session.getUser().getFirstName() + " " + session.getUser().getLastName());
            }

            invoice.setCompanyOne(companyOne);
            invoice.setCompanyTwo(companyTwo);
        } else {
            companyOne = invoice.getCompanyOne();
            companyTwo = invoice.getCompanyTwo();
        }

        loadInvoiceData();
    }

    private void saveInvoiceData() {
        companyOne.setName(companyOneName.getText());
        companyOne.setAddress(invoiceService.getAddress(companyOneAddress.getText()));
        companyOne.setDdsNumber(companyOneDDSNumber.getText());
        companyOne.setBulstat(companyOneEIK.getText());
        companyOne.setMol(companyOneMOL.getText());

        companyTwo.setName(companyTwoName.getText());
        companyTwo.setAddress(invoiceService.getAddress(companyTwoAddress.getText()));
        companyTwo.setDdsNumber(companyTwoDDSNumber.getText());
        companyTwo.setBulstat(companyTwoEIK.getText());
        companyTwo.setMol(companyTwoMOL.getText());

        invoice.setCompanyOne(companyOne);
        invoice.setCompanyTwo(companyTwo);
        invoice.setBuyer(buyerName.getText());
        invoice.setSeller(sellerName.getText());

        paymentBean.setInvoice(invoice);
    }

    private void loadInvoiceData() {
        companyOneName.setText(companyOne.getName());
        companyOneAddress.setText(companyOne.getAddress() != null ? companyOne.getAddress().getAddress() : "");
        companyOneDDSNumber.setText(companyOne.getDdsNumber());
        companyOneEIK.setText(companyOne.getBulstat());
        companyOneMOL.setText(companyOne.getMol());

        companyTwoName.setText(companyTwo.getName());
        companyTwoAddress.setText(companyTwo.getAddress() != null ? companyTwo.getAddress().getAddress() : "");
        companyTwoDDSNumber.setText(companyTwo.getDdsNumber());
        companyTwoEIK.setText(companyTwo.getBulstat());
        companyTwoMOL.setText(companyTwo.getMol());

        buyerName.setText(invoice.getBuyer());
        buyerDate.setText(new SimpleDateFormat("dd.MM.yyyy").format(invoice.getCreatedAt()));
        sellerName.setText(invoice.getSeller());
    }

    private void lockCompanyOne() {
        companyOneName.setEditable(false);
        companyOneAddress.setEditable(false);
        companyOneDDSNumber.setEditable(false);
        companyOneEIK.setEditable(false);
        companyOneMOL.setEditable(false);

        buyerName.setEditable(false);
        buyerDate.setEditable(false);
    }

    private void lockCompanyTwo() {
        companyTwoName.setEditable(false);
        companyTwoAddress.setEditable(false);
        companyTwoDDSNumber.setEditable(false);
        companyTwoEIK.setEditable(false);
        companyTwoMOL.setEditable(false);

        sellerName.setEditable(false);
    }
}
