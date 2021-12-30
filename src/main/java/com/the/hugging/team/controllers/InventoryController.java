package com.the.hugging.team.controllers;

import com.the.hugging.team.entities.Product;
import com.the.hugging.team.entities.User;
import com.the.hugging.team.services.ProductService;
import com.the.hugging.team.utils.Dialogs;
import com.the.hugging.team.utils.Session;
import com.the.hugging.team.utils.TableResizer;
import com.the.hugging.team.utils.WindowHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class InventoryController extends WindowHandler {

    private final ProductService productService = ProductService.getInstance();
    private final User user = Session.getInstance().getUser();

    @FXML
    private TableView<Product> table;
    @FXML
    private TableColumn<Product, String> nomenclature;
    @FXML
    private TableColumn<Product, String> category;
    @FXML
    private TableColumn<Product, String> name;
    @FXML
    private TableColumn<Product, String> quantity;
    @FXML
    private TableColumn<Product, String> retailPrice;
    @FXML
    private TableColumn<Product, String> wholesalePrice;
    @FXML
    private TableColumn<Product, String> deliveryPrice;
    @FXML
    private TableColumn<Product, String> shelf;
    @FXML
    private TextField searchField;
    @FXML
    private VBox sideBox;
    @FXML
    private Button createButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;

    private ObservableList<Product> data;
    private FilteredList<Product> filteredList;

    public void initialize() {
        data = FXCollections.observableArrayList(productService.getAllProducts());

        filteredList = new FilteredList<>(data, p -> true);

        nomenclature.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomenclature()));
        category.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductCategory().getName()));
        name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        quantity.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getQuantity().toString() + " " + cellData.getValue().getProductQuantityType().getName()));
        retailPrice.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRetailPrice().toString()));
        wholesalePrice.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getWholesalePrice().toString()));
        deliveryPrice.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDeliveryPrice().toString()));
        shelf.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getShelf().getName()));

        table.getItems().setAll(filteredList);
        TableResizer.setDefault(table);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() == 0) {
                filteredList.setPredicate(p -> true);
                table.getItems().setAll(filteredList);
            }
        });

        checkPermissions();
    }

    @FXML
    private void search(ActionEvent event) {
        String search = searchField.getText();

        filteredList.setPredicate(product -> product.getName().contains(search) || product.getNomenclature().contains(search));
        table.getItems().setAll(filteredList);
    }

    @FXML
    private void create(ActionEvent event) {
        Dialogs.productDialog(new Product(), "Create product").ifPresent(product ->
        {
            data.add(productService.addProduct(product));
            table.getItems().setAll(filteredList);
        });
    }

    @FXML
    private void edit(ActionEvent event) {
        Product product = table.getSelectionModel().getSelectedItem();

        if (product == null) Dialogs.notSelectedWarning();
        else Dialogs.productDialog(product, "Edit " + product.getName()).ifPresent(editedProduct ->
        {
            productService.updateProduct(editedProduct);
            table.refresh();
        });
    }

    @FXML
    private void delete(ActionEvent event) {
        Product product = table.getSelectionModel().getSelectedItem();

        if (product == null) Dialogs.notSelectedWarning();
        else {
            data.remove(product);
            table.getItems().setAll(filteredList);
            productService.deleteProduct(product);
        }
    }

    private void checkPermissions() {
        if (!user.can("permissions.products.create")) {
            sideBox.getChildren().remove(createButton);
        }
        if (!user.can("permissions.products.edit")) {
            sideBox.getChildren().remove(editButton);
        }
        if (!user.can("permissions.products.delete")) {
            sideBox.getChildren().remove(deleteButton);
        }
    }
}
