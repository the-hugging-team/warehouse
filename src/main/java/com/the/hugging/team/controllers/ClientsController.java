package com.the.hugging.team.controllers;

import com.the.hugging.team.entities.Client;
import com.the.hugging.team.services.ClientService;
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

@SuppressWarnings("DuplicatedCode")
public class ClientsController extends DashboardTemplate {

    private final ClientService clientService = ClientService.getInstance();
    @FXML
    private TableView<Object> table;
    @FXML
    private TableColumn<Client, String> name;
    @FXML
    private TableColumn<Client, String> id;
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

    private ObservableList<Client> data;
    private FilteredList<Client> filteredList;

    public void initialize() {
        data = FXCollections.observableArrayList(clientService.getAllClients());

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

        checkPermissions();
    }

    public void search(ActionEvent e) {
        String search = searchField.getText();

        filteredList.setPredicate(client -> client.getName().contains(search));
        table.getItems().setAll(filteredList);
    }

    public void create(ActionEvent e) {
        Dialogs.singleTextInputDialog("Name", "Create client", "Enter the name: ").ifPresent(name ->
        {
            data.add(clientService.addClient(name));
            table.getItems().setAll(filteredList);
        });
    }

    public void edit(ActionEvent e) {
        Client client = (Client) table.getSelectionModel().getSelectedItem();

        if (client == null) Dialogs.NotSelectedWarning();
        else Dialogs.singleTextInputDialog(client.getName(), "Edit name", "Enter new name: ").ifPresent(name ->
        {
            clientService.setClientName(client, name);
            table.refresh();
        });
    }

    public void delete(ActionEvent e) {
        Client client = (Client) table.getSelectionModel().getSelectedItem();

        if (client == null) Dialogs.NotSelectedWarning();
        else {
            data.remove(client);
            table.getItems().setAll(filteredList);
            clientService.deleteClient(client);
        }
    }

    private void checkPermissions() {
        if (!user.can("permissions.clients.create")) {
            sideBox.getChildren().remove(createButton);
        }
        if (!user.can("permissions.clients.edit")) {
            sideBox.getChildren().remove(editButton);
        }
        if (!user.can("permissions.clients.delete")) {
            sideBox.getChildren().remove(deleteButton);
        }
    }
}
