package com.the.hugging.team.controllers;

import com.the.hugging.team.entities.User;
import com.the.hugging.team.services.ActivityService;
import com.the.hugging.team.services.ActivityTypeService;
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
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class UserController extends WindowHandler {
    private final UserService userService = UserService.getInstance();
    private final Session session = Session.getInstance();
    private final User user = session.getUser();
    private final ActivityService activityService = ActivityService.getInstance();
    private final ActivityTypeService activityTypeService = ActivityTypeService.getInstance();


    @FXML
    private TableView<User> table;

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

    @FXML
    private Button showActivityHistoryButton;

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

        table.setRowFactory(tv -> {
            final TableRow<User> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    showActivityHistory(new ActionEvent());
                }
            });

            return row;
        });

        checkPermissions();
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
            activityService.addActivity(activityTypeService.getActivityTypeBySlug("activity-types.users.create"));
        });
    }

    @FXML
    private void edit(ActionEvent event) {
        User userSelected = table.getSelectionModel().getSelectedItem();

        if (userSelected == null) Dialogs.notSelectedWarning();
        else Dialogs.userDialog(userSelected, "Edit user").ifPresent(user ->
        {
            userService.updateUser(user);
            table.refresh();
            activityService.addActivity(activityTypeService.getActivityTypeBySlug("activity-types.users.edit"));
        });
    }

    @FXML
    private void showActivityHistory(ActionEvent event) {
        User userSelected = table.getSelectionModel().getSelectedItem();

        if (userSelected == null) Dialogs.notSelectedWarning();
        else Dialogs.userActivitiesDialog(userSelected);
    }

    private void checkPermissions() {
        if (!user.can("permissions.users.create")) {
            sideBox.getChildren().remove(createButton);
        }
        if (!user.can("permissions.users.edit")) {
            sideBox.getChildren().remove(editButton);
        }
        if (!user.can("permissions.users.show-activity-history")) {
            sideBox.getChildren().remove(showActivityHistoryButton);
        }
    }
}
