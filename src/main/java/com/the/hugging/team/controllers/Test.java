package com.the.hugging.team.controllers;

import com.the.hugging.team.entities.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class Test extends DashboardTemplate {

    @FXML
    private TableView<User> table;

    @FXML
    private TableColumn<User, String> firstName;

    @FXML
    private TableColumn<User, String> lastName;

    @FXML
    private TableColumn<User, String> sex;

    @FXML
    private TextField searchField;

    private FilteredList<User> filteredList;

    public void initialize() {
        User user1 = new User();
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setSex(1);

        User user2 = new User();
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setSex(0);

        User user3 = new User();
        user3.setFirstName("John4");
        user3.setLastName("Smith");
        user3.setSex(1);

        User user4 = new User();
        user4.setFirstName("Jane1");
        user4.setLastName("Smith");
        user4.setSex(0);

        User user5 = new User();
        user5.setFirstName("John3");
        user5.setLastName("Doe");
        user5.setSex(1);

        ObservableList<User> data = FXCollections.observableArrayList(user1, user2, user3, user4, user5);

        filteredList = new FilteredList<>(data, p -> true);

        firstName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
        lastName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
        sex.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSex() == 1 ? "Male" : "Female"));

        table.getItems().setAll(filteredList);
    }

    public void edit(ActionEvent e) {
        User user = table.getSelectionModel().getSelectedItem();

        System.out.println(user);
    }

    public void search(ActionEvent e) {
        String search = searchField.getText();
        System.out.println(search);

        filteredList.setPredicate(user -> user.getFirstName().contains(search) || user.getLastName().contains(search));

        table.getItems().setAll(filteredList);
    }
}
