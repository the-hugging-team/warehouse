package com.the.hugging.team.controllers;

import com.the.hugging.team.entities.Shelf;
import com.the.hugging.team.entities.User;
import com.the.hugging.team.services.StorageService;
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

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("DuplicatedCode")
public class ShelfController extends WindowHandler {

    private final StorageService storageService = StorageService.getInstance();
    private final Session session = Session.getInstance();
    private final User user = session.getUser();
    @FXML
    private TableView<Object> table;
    @FXML
    private TableColumn<Shelf, String> name;
    @FXML
    private TableColumn<Shelf, String> id;
    @FXML
    private TextField searchField;
    @FXML
    private VBox sideBox;
    @FXML
    private Button createButton;
    @FXML
    private Button editButton;
    @FXML
    private Button showButton;
    @FXML
    private Button deleteButton;

    private ObservableList<Shelf> data;
    private FilteredList<Shelf> filteredList;

    public void initialize() {
        data = FXCollections.observableArrayList(storageService.getAllShelves());

        filteredList = new FilteredList<>(data, p -> true);

        name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        id.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));

        table.getItems().setAll(filteredList);
        TableResizer.setCustomColumns(table, List.of(0, 2), List.of(100, 200));

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() == 0) {
                filteredList.setPredicate(p -> true);
                table.getItems().setAll(filteredList);
            }
        });

        checkPermissions();
    }

    public void search(ActionEvent e) {
        String search = searchField.getText();

        filteredList.setPredicate(client -> client.getName().contains(search));
        table.getItems().setAll(filteredList);
    }

    public void create(ActionEvent e) {
        Dialogs.singleTextInputDialog("Name", "Create shelf", "Enter the name: ").ifPresent(name ->
        {
            data.add(storageService.addShelf(name, Session.getInstance().getSelectedRoom()));
            table.getItems().setAll(filteredList);
        });
    }

    public void edit(ActionEvent e) {
        Shelf shelf = (Shelf) table.getSelectionModel().getSelectedItem();

        if (shelf == null) Dialogs.notSelectedWarning();
        else Dialogs.singleTextInputDialog(shelf.getName(), "Edit name", "Enter new name: ").ifPresent(name ->
        {
            storageService.setShelfName(shelf, name);
            table.refresh();
        });
    }

    public void delete(ActionEvent e) {
        Shelf shelf = (Shelf) table.getSelectionModel().getSelectedItem();

        if (shelf == null) Dialogs.notSelectedWarning();
        else {
            data.remove(shelf);
            table.getItems().setAll(filteredList);
            storageService.deleteShelf(shelf);
        }
    }

    public void show(ActionEvent e) {
        Shelf shelf = (Shelf) table.getSelectionModel().getSelectedItem();

        if (shelf == null) Dialogs.notSelectedWarning();
        else {
            Dialogs.shelfItemsDialog(shelf);
        }
    }

    private void checkPermissions() {
        if (!user.can("permissions.shelves.create")) {
            sideBox.getChildren().remove(createButton);
        }
        if (!user.can("permissions.shelves.edit")) {
            sideBox.getChildren().remove(editButton);
        }
        if (!user.can("permissions.shelves.delete")) {
            sideBox.getChildren().remove(deleteButton);
        }
        if (!user.can("permissions.products.index")) {
            sideBox.getChildren().remove(showButton);
        }
    }
}
