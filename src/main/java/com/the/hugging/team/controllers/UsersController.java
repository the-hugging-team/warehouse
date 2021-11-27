package com.the.hugging.team.controllers;

import com.the.hugging.team.entities.User;
import com.the.hugging.team.services.UserService;
import com.the.hugging.team.utils.TableResizer;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.List;

public class UsersController extends DashboardTemplate {

    private final UserService userService = UserService.getInstance();

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

    private FilteredList<User> filteredList;

    @FXML
    public void initialize() {
        List<User> users = userService.getAllUsers();

        ObservableList<User> data = FXCollections.observableArrayList(users);

        filteredList = new FilteredList<>(data, p -> true);

        firstName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
        lastName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
        sex.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSexFormatted()));
        username.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
        roleName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRole().getName()));
        createdAt.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCreatedAtFormatted()));

        table.getItems().setAll(filteredList);
        TableResizer.setResizer(table);
    }

    @FXML
    private void search() {
        String search = searchField.getText();
        System.out.println(search);

        filteredList.setPredicate(user -> user.getFirstName().contains(search) || user.getLastName().contains(search) || user.getSexFormatted().contains(search));

        table.getItems().setAll(filteredList);
    }

    @FXML
    private void add() {
    }

    @FXML
    private void edit() {
    }
}
