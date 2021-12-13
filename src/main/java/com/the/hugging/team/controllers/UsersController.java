package com.the.hugging.team.controllers;

import com.the.hugging.team.entities.User;
import com.the.hugging.team.services.UserService;
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

public class UsersController extends WindowHandler {

    private final UserService userService = UserService.getInstance();
    private final Session session = Session.getInstance();
    private final User user = session.getUser();

    @FXML
    private TableView<Object> table;

    @FXML
    private TableColumn<User, String> firstName;

    @FXML
    private TableColumn<User, String> lastName;

    @FXML
    private TableColumn<User, String> username;

    @FXML
    private TableColumn<User, String> roleName;

    @FXML
    private TableColumn<User, String> createdAt;

    @FXML
    private TableColumn<User, String> sex;

    @FXML
    private TextField searchField;

    @FXML
    private VBox sideBox;

    @FXML
    private Button createButton;

    @FXML
    private Button editButton;

    private ObservableList<User> data;
    private FilteredList<User> filteredList;

    @FXML
    public void initialize() {
        data = FXCollections.observableArrayList(userService.getAllUsers());

        filteredList = new FilteredList<>(data, p -> true);

        firstName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
        lastName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
        sex.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSexFormatted()));
        username.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
        roleName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRole().getName()));
        createdAt.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCreatedAtFormatted()));

        table.getItems().setAll(filteredList);
        TableResizer.setDefault(table);

        if (!user.can("permissions.clients.create")) {
            sideBox.getChildren().remove(createButton);
        }
        if (!user.can("permissions.clients.edit")) {
            sideBox.getChildren().remove(editButton);
        }
    }

    @FXML
    private void search() {
        String search = searchField.getText();
        System.out.println(search);

        filteredList.setPredicate(user -> user.getFirstName().contains(search) || user.getLastName().contains(search) || user.getSexFormatted().contains(search));

        table.getItems().setAll(filteredList);
    }

    @FXML
    private void create(ActionEvent event) {
        Dialogs.userDialog(new User(), "Create user").ifPresent(user ->
        {
            data.add(userService.addUser(user));
            table.getItems().setAll(filteredList);
        });
    }

    @FXML
    private void edit(ActionEvent event) {
        User userSelected = (User) table.getSelectionModel().getSelectedItem();

        if (userSelected == null) Dialogs.NotSelectedWarning();
        else Dialogs.userDialog(userSelected, "Edit user").ifPresent(user ->
        {
            userService.updateUser(user);
            table.refresh();
        });
    }
}
