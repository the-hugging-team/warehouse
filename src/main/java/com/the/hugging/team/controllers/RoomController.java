package com.the.hugging.team.controllers;

import com.the.hugging.team.entities.Room;
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
public class RoomController extends WindowHandler {

    private final StorageService storageService = StorageService.getInstance();
    private final Session session = Session.getInstance();
    private final User user = session.getUser();
    @FXML
    private TableView<Object> table;
    @FXML
    private TableColumn<Room, String> name;
    @FXML
    private TableColumn<Room, String> id;
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

    private ObservableList<Room> data;
    private FilteredList<Room> filteredList;

    public void initialize() {
        data = FXCollections.observableArrayList(storageService.getAllRooms());

        filteredList = new FilteredList<>(data, p -> true);

        name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        id.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));

        table.getItems().setAll(filteredList);
        TableResizer.setCustomColumns(table, List.of(0), List.of(100));

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
        Dialogs.singleTextInputDialog("Name", "Create room", "Enter the name: ").ifPresent(name ->
        {
            data.add(storageService.addRoom(name));
            table.getItems().setAll(filteredList);
        });
    }

    public void edit(ActionEvent e) {
        Room room = (Room) table.getSelectionModel().getSelectedItem();

        if (room == null) Dialogs.notSelectedWarning();
        else Dialogs.singleTextInputDialog(room.getName(), "Edit name", "Enter new name: ").ifPresent(name ->
        {
            storageService.setRoomName(room, name);
            table.refresh();
        });
    }

    public void delete(ActionEvent e) {
        Room room = (Room) table.getSelectionModel().getSelectedItem();

        if (room == null) Dialogs.notSelectedWarning();
        else {
            data.remove(room);
            table.getItems().setAll(filteredList);
            storageService.deleteRoom(room);
        }
    }

    public void show(ActionEvent e) {
        Room room = (Room) table.getSelectionModel().getSelectedItem();

        if (room == null) Dialogs.notSelectedWarning();
        else {
            DashboardTemplate.getInstance().loadView("views/dashboard/cruds/shelves-crud.fxml");
            Session.getInstance().setSelectedRoom(room);
        }
    }

    private void checkPermissions() {
        if (!user.can("permissions.rooms.create")) {
            sideBox.getChildren().remove(createButton);
        }
        if (!user.can("permissions.rooms.edit")) {
            sideBox.getChildren().remove(editButton);
        }
        if (!user.can("permissions.rooms.delete")) {
            sideBox.getChildren().remove(deleteButton);
        }
        if (!user.can("permissions.shelves.index")) {
            sideBox.getChildren().remove(showButton);
        }
    }
}
