package com.the.hugging.team.controllers;

import com.the.hugging.team.entities.Supplier;
import com.the.hugging.team.repositories.SupplierRepository;
import com.the.hugging.team.services.SupplierService;
import com.the.hugging.team.utils.Dialogs;
import com.the.hugging.team.utils.TableResizer;
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

import java.util.ArrayList;
import java.util.List;

public class SupplierController extends DashboardTemplate {

    private final SupplierService supplierService = SupplierService.getInstance();
    @FXML
    private TableView<Object> table;
    @FXML
    private TableColumn<Supplier, String> name;
    @FXML
    private TableColumn<Supplier, String> id;
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

    private ObservableList<Supplier> data;
    private FilteredList<Supplier> filteredList;

    public void initialize() {
        data = FXCollections.observableArrayList(SupplierRepository.getInstance().getAll());

        filteredList = new FilteredList<>(data, p -> true);

        name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        id.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));

        table.getItems().setAll(filteredList);
        TableResizer.setCustomColumns(table, new ArrayList<>(List.of(0)), new ArrayList<>(List.of(100)));

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() == 0) {
                filteredList.setPredicate(p -> true);
                table.getItems().setAll(filteredList);
            }
        });

        if (!user.can("permissions.suppliers.create")) {
            sideBox.getChildren().remove(createButton);
        }
        if (!user.can("permissions.suppliers.edit")) {
            sideBox.getChildren().remove(editButton);
        }
        if (!user.can("permissions.suppliers.delete")) {
            sideBox.getChildren().remove(deleteButton);
        }
    }

    public void search(ActionEvent e) {
        String search = searchField.getText();

        filteredList.setPredicate(client -> client.getName().contains(search));

        table.getItems().setAll(filteredList);
    }

    public void create(ActionEvent e) {
        Dialogs.singleTextInputDialog("Name", "Create supplier", "Enter the name: ").ifPresent(name ->
        {
            data.add(supplierService.addSupplier(name));
            table.getItems().setAll(filteredList);
        });
    }

    public void edit(ActionEvent e) {
        Supplier supplier = (Supplier) table.getSelectionModel().getSelectedItem();

        if (supplier == null) Dialogs.NotSelectedWarning();
        else Dialogs.singleTextInputDialog(supplier.getName(), "Edit name", "Enter new name: ").ifPresent(name ->
        {
            supplierService.setSupplierName(supplier, name);
            table.refresh();
        });
    }

    public void delete(ActionEvent e) {
        Supplier supplier = (Supplier) table.getSelectionModel().getSelectedItem();

        if (supplier == null) Dialogs.NotSelectedWarning();
        else {
            data.remove(supplier);
            table.getItems().setAll(filteredList);
            supplierService.deleteSupplier(supplier);
        }
    }
}
