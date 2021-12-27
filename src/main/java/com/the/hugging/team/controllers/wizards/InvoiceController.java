package com.the.hugging.team.controllers.wizards;

import com.the.hugging.team.entities.Company;
import com.the.hugging.team.entities.Invoice;
import com.the.hugging.team.services.CompanyService;
import com.the.hugging.team.services.InvoiceService;
import com.the.hugging.team.utils.Dialogs;
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
    private boolean canChangeStep = true;

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
        saveInvoiceData();
        if (canChangeStep) {
            eventSource.fire(EventType.NEXT_STEP_EVENT_TYPE, new NextStepEvent());
        }
    }

    @FXML
    public void prevStep() {
        saveInvoiceData();
        if (canChangeStep) {
            eventSource.fire(EventType.PREVIOUS_STEP_EVENT_TYPE, new PreviousStepEvent());
        }
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
        String errors = "";

        if (companyOneName.getText() == null || companyOneName.getText().isEmpty()) {
            errors += "Company one name is required\n";
        } else {
            companyOne.setName(companyOneName.getText());
        }

        if (companyOneAddress.getText() == null || companyOneAddress.getText().isEmpty()) {
            errors += "Company one address is required\n";
        } else {
            companyOne.setAddress(invoiceService.getAddress(companyOneAddress.getText()));
        }

        if (companyOneDDSNumber.getText() == null || companyOneDDSNumber.getText().isEmpty()) {
            errors += "Company one DDS number is required\n";
        } else if (!companyOneDDSNumber.getText().matches("BG[0-9]{9}")) {
            errors += "Company one DDS number is not valid\n";
        } else {
            companyOne.setDdsNumber(companyOneDDSNumber.getText());
        }

        if (companyOneEIK.getText() == null || companyOneEIK.getText().isEmpty()) {
            errors += "Company one EIK is required\n";
        } else if (!companyOneEIK.getText().matches("[0-9]{9}|[0-9]{10}|[0-9]{13}")) {
            errors += "Company one EIK is not valid\n";
        } else {
            companyOne.setBulstat(companyOneEIK.getText());
        }

        if (companyOneMOL.getText() == null || companyOneMOL.getText().isEmpty()) {
            errors += "Company one MOL is required\n";
        } else {
            companyOne.setMol(companyOneMOL.getText());
        }


        if (companyTwoName.getText() == null || companyTwoName.getText().isEmpty()) {
            errors += "Company two name is required\n";
        } else {
            companyTwo.setName(companyTwoName.getText());
        }

        if (companyTwoAddress.getText() == null || companyTwoAddress.getText().isEmpty()) {
            errors += "Company two address is required\n";
        } else {
            companyTwo.setAddress(invoiceService.getAddress(companyTwoAddress.getText()));
        }

        if (companyTwoDDSNumber.getText() == null || companyTwoDDSNumber.getText().isEmpty()) {
            errors += "Company two DDS number is required\n";
        } else if (!companyTwoDDSNumber.getText().matches("BG[0-9]{9}")) {
            errors += "Company two DDS number is not valid\n";
        } else {
            companyTwo.setDdsNumber(companyTwoDDSNumber.getText());
        }

        if (companyTwoEIK.getText() == null || companyTwoEIK.getText().isEmpty()) {
            errors += "Company two EIK is required\n";
        } else if (!companyTwoEIK.getText().matches("[0-9]{9}|[0-9]{10}|[0-9]{13}")) {
            errors += "Company two EIK is not valid\n";
        } else {
            companyTwo.setBulstat(companyTwoEIK.getText());
        }

        if (companyTwoMOL.getText() == null || companyTwoMOL.getText().isEmpty()) {
            errors += "Company two MOL is required\n";
        } else {
            companyTwo.setMol(companyTwoMOL.getText());
        }

        if (!errors.isEmpty()) {
            canChangeStep = false;
            Dialogs.warningDialog("Error", errors);
            return;
        }

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
