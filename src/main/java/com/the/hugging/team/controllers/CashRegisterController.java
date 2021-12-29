package com.the.hugging.team.controllers;

import com.the.hugging.team.entities.CashRegister;
import com.the.hugging.team.entities.User;
import com.the.hugging.team.services.ActivityService;
import com.the.hugging.team.services.ActivityTypeService;
import com.the.hugging.team.services.CashRegisterService;
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

public class CashRegisterController extends WindowHandler {

    private final CashRegisterService cashRegistersService = CashRegisterService.getInstance();
    private final Session session = Session.getInstance();
    private final User user = session.getUser();
    private final ActivityService activityService = ActivityService.getInstance();
    private final ActivityTypeService activityTypeService = ActivityTypeService.getInstance();

    @FXML
    private TableView<CashRegister> table;
    @FXML
    private TableColumn<CashRegister, String> userAssigned;
    @FXML
    private TableColumn<CashRegister, String> id;
    @FXML
    private TableColumn<CashRegister, String> balance;
    @FXML
    private TextField searchField;
    @FXML
    private VBox sideBox;
    @FXML
    private Button createButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button operateButton;
    @FXML
    private Button showSaleHistoryButton;

    private ObservableList<CashRegister> data;
    private FilteredList<CashRegister> filteredList;

    public void initialize() {
        data = FXCollections.observableArrayList(cashRegistersService.getAllCashRegisters());

        filteredList = new FilteredList<>(data, p -> true);

        userAssigned.setCellValueFactory(cellData ->
        {
            String textData;
            if (cellData.getValue().getUser() != null)
                textData = cellData.getValue().getUser().getFirstName() + " " + cellData.getValue().getUser().getLastName();
            else textData = "No user assigned";
            return new SimpleStringProperty(textData);
        });
        id.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        balance.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBalance().toString()));

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

    public void search(ActionEvent e) {
        String search = searchField.getText();
        int enteredId = Integer.parseInt(search);
        filteredList.setPredicate(client -> client.getId() == enteredId);
        table.getItems().setAll(filteredList);
    }

    public void create(ActionEvent e) {
        data.add(cashRegistersService.addCashRegister());
        table.getItems().setAll(filteredList);
        activityService.addActivity(activityTypeService.getActivityTypeBySlug("activities.cash-registers.create"));
    }

    public void showSaleHistory(ActionEvent e) {
        CashRegister cr = table.getSelectionModel().getSelectedItem();
        if (cr == null) Dialogs.notSelectedWarning();
        else Dialogs.cashRegisterSaleHistoryDialog(cr);
    }

    public void delete(ActionEvent e) {
        CashRegister cr = table.getSelectionModel().getSelectedItem();

        if (cr == null) Dialogs.notSelectedWarning();
        else {
            data.remove(cr);
            table.getItems().setAll(filteredList);
            cashRegistersService.deleteCashRegister(cr);
            activityService.addActivity(activityTypeService.getActivityTypeBySlug("activities.cash-registers.delete"));
        }
    }

    private void checkPermissions() {
        if (!user.can("permissions.cash-registers.create")) {
            sideBox.getChildren().remove(createButton);
        }
        if (!user.can("permissions.cash-registers.delete")) {
            sideBox.getChildren().remove(deleteButton);
        }
        if (!user.can("permissions.cash-registers.operate")) {
            sideBox.getChildren().remove(operateButton);
        }
        if (!user.can("permissions.transaction.index")) {
            sideBox.getChildren().remove(showSaleHistoryButton);
        }
    }
}
