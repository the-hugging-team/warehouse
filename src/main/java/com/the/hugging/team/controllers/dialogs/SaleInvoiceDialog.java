package com.the.hugging.team.controllers.dialogs;

import com.the.hugging.team.entities.Company;
import com.the.hugging.team.entities.Invoice;
import com.the.hugging.team.entities.Sale;
import com.the.hugging.team.entities.SaleProduct;
import com.the.hugging.team.utils.TableResizer;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class SaleInvoiceDialog extends Dialog<Sale> {

    private Sale sale;

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
    private TableView<SaleProduct> productTable;

    @FXML
    private TableColumn<SaleProduct, String> number;

    @FXML
    private TableColumn<SaleProduct, String> nomenclature;

    @FXML
    private TableColumn<SaleProduct, String> name;

    @FXML
    private TableColumn<SaleProduct, String> quantityType;

    @FXML
    private TableColumn<SaleProduct, String> quantity;

    @FXML
    private TableColumn<SaleProduct, String> singlePrice;

    @FXML
    private TableColumn<SaleProduct, String> totalPrice;

    @FXML
    private TextField buyerDate;

    @FXML
    private TextField buyerName;

    @FXML
    private TextField sellerName;

    @FXML
    private TextField basePrice;

    @FXML
    private TextField ddsPercentage;

    @FXML
    private TextField ddsValue;

    @FXML
    private TextField finalPrice;

    public SaleInvoiceDialog(Sale sale, Window owner) {
        this.sale = sale;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("views/dialogs/invoice-dialog.fxml"));
        loader.setController(this);

        DialogPane dialogPane = new DialogPane();
        try {
            dialogPane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        initOwner(owner);
        initModality(Modality.APPLICATION_MODAL);

        ((Stage) getDialogPane().getScene().getWindow()).getIcons().add(new Image(com.the.hugging.team.utils.Window.CELLABLUE_PATH));

        setResizable(false);
        setTitle("Invoice " + sale.getInvoice().getId());
        setDialogPane(dialogPane);
        setGraphic(null);
    }

    public void initialize() {
        ObservableList<SaleProduct> data = FXCollections.observableArrayList(sale.getSaleProducts());

        FilteredList<SaleProduct> filteredList = new FilteredList<>(data, p -> true);

        number.setCellValueFactory(cellData -> new SimpleStringProperty((productTable.getItems().indexOf(cellData.getValue()) + 1) + ""));
        nomenclature.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduct().getNomenclature()));
        name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduct().getName()));
        quantityType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductQuantityType().getName()));
        quantity.setCellValueFactory(cellData -> new SimpleStringProperty(String.format("%.2f", cellData.getValue().getQuantity())));
        singlePrice.setCellValueFactory(cellData -> new SimpleStringProperty(String.format("%.2f", cellData.getValue().getProduct().getWholesalePrice())));
        totalPrice.setCellValueFactory(cellData -> new SimpleStringProperty(String.format("%.2f", (cellData.getValue().getProduct().getWholesalePrice() * cellData.getValue().getQuantity()))));

        productTable.getItems().setAll(filteredList);
        TableResizer.setDefault(productTable);

        Invoice invoice = sale.getInvoice();

        Company companyOne = invoice.getCompanyOne();
        Company companyTwo = invoice.getCompanyTwo();

        companyOneName.setText(companyOne.getName());
        companyOneAddress.setText(companyOne.getAddress().getAddress());
        companyOneDDSNumber.setText(companyOne.getDdsNumber());
        companyOneEIK.setText(companyOne.getBulstat());
        companyOneMOL.setText(companyOne.getMol());

        companyTwoName.setText(companyTwo.getName());
        companyTwoAddress.setText(companyTwo.getAddress().getAddress());
        companyTwoDDSNumber.setText(companyTwo.getDdsNumber());
        companyTwoEIK.setText(companyTwo.getBulstat());
        companyTwoMOL.setText(companyTwo.getMol());

        buyerDate.setText(sale.getCreatedAt().toString());
        buyerName.setText(invoice.getBuyer());
        sellerName.setText(invoice.getSeller());

        basePrice.setText(String.format("%.2f", invoice.getBasePrice()));
        ddsPercentage.setText(String.format("%.2f", 100 / (invoice.getBasePrice() / invoice.getDds())));
        ddsValue.setText(String.format("%.2f", invoice.getDds()));
        finalPrice.setText(String.format("%.2f", invoice.getTotalPrice()));
    }
}
