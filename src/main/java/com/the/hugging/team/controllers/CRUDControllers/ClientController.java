package com.the.hugging.team.controllers.CRUDControllers;

import com.the.hugging.team.controllers.DashboardTemplate;
import com.the.hugging.team.entities.Client;
import com.the.hugging.team.repositories.ClientRepository;
import com.the.hugging.team.services.ClientService;
import com.the.hugging.team.utils.TableResizer;
import com.the.hugging.team.utils.Window;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.*;

public class ClientController extends DashboardTemplate {

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

    private Client getSelected() {
        Client client = (Client) table.getSelectionModel().getSelectedItem();
        if (client == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Window.CELLABLUE_PATH));
            alert.setTitle("Warning: Client not selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a client!");

            alert.showAndWait();
        }

        return client;
    }

    public void initialize() {
        data = FXCollections.observableArrayList(ClientRepository.getInstance().getAll());

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

    public void search(ActionEvent e) {
        String search = searchField.getText();

        filteredList.setPredicate(client -> client.getName().contains(search));

        table.getItems().setAll(filteredList);
    }

    public void create(ActionEvent e) {
        TextInputDialog dialog = new TextInputDialog("Name");
        ((Stage) dialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Window.CELLABLUE_PATH));
        dialog.setTitle("Create client");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter the name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name ->
        {
            data.add(clientService.addClient(name));
            table.getItems().setAll(filteredList);
        });
    }

    public void edit(ActionEvent e) {
        Client client = this.getSelected();
        //create dialog for client editing
        TextInputDialog dialog = new TextInputDialog(Objects.requireNonNull(client).getName());
        ((Stage) dialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Window.CELLABLUE_PATH));
        dialog.setTitle("Edit Client");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter the new name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name ->
        {
            clientService.setClientName(client, name);
            table.refresh();
        });
    }

    public void delete(ActionEvent e) {
        Client client = this.getSelected();
        data.remove(client);
        table.getItems().setAll(filteredList);
        clientService.deleteClient(client);
    }
}
