package com.the.hugging.team.controllers;

import com.the.hugging.team.entities.Company;
import com.the.hugging.team.services.ActivityService;
import com.the.hugging.team.services.ActivityTypeService;
import com.the.hugging.team.services.CompanyService;
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

public class CompanyController extends DashboardTemplate {

    private final CompanyService companiesService = CompanyService.getInstance();
    private final ActivityService activityService = ActivityService.getInstance();
    private final ActivityTypeService activityTypeService = ActivityTypeService.getInstance();

    @FXML
    private TableView<Company> table;
    @FXML
    private TableColumn<Company, String> name;
    @FXML
    private TableColumn<Company, String> id;
    @FXML
    private TableColumn<Company, String> address;
    @FXML
    private TableColumn<Company, String> bulstat;
    @FXML
    private TableColumn<Company, String> dds;
    @FXML
    private TableColumn<Company, String> mol;
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

    private ObservableList<Company> data;
    private FilteredList<Company> filteredList;

    public void initialize() {
        data = FXCollections.observableArrayList(companiesService.getAllCompanies());

        filteredList = new FilteredList<>(data, p -> true);

        id.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        address.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getAddress()));
        bulstat.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBulstat()));
        dds.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDdsNumber()));
        mol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMol()));

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

        filteredList.setPredicate(company -> company.getName().contains(search));
        table.getItems().setAll(filteredList);
    }

    public void create(ActionEvent e) {
        Dialogs.companyDialog(new Company(), "Create company").ifPresent(company ->
        {
            data.add(companiesService.addCompany(company));
            table.getItems().setAll(filteredList);
            activityService.addActivity(activityTypeService.getActivityTypeBySlug("activities.companies.create"));
        });
    }

    public void edit(ActionEvent e) {
        Company company = table.getSelectionModel().getSelectedItem();

        if (company == null) Dialogs.notSelectedWarning();
        else Dialogs.companyDialog(company, "Edit " + company.getName() + " company").ifPresent(editedCompany ->
        {
            companiesService.updateCompany(editedCompany);
            table.refresh();
            activityService.addActivity(activityTypeService.getActivityTypeBySlug("activities.companies.edit"));
        });
    }

    public void delete(ActionEvent e) {
        Company company = table.getSelectionModel().getSelectedItem();

        if (company == null) Dialogs.notSelectedWarning();
        else {
            data.remove(company);
            table.getItems().setAll(filteredList);
            companiesService.deleteCompany(company);
            activityService.addActivity(activityTypeService.getActivityTypeBySlug("activities.companies.delete"));
        }
    }

    private void checkPermissions() {
        if (!user.can("permissions.companies.create")) {
            sideBox.getChildren().remove(createButton);
        }
        if (!user.can("permissions.companies.edit")) {
            sideBox.getChildren().remove(editButton);
        }
        if (!user.can("permissions.companies.delete")) {
            sideBox.getChildren().remove(deleteButton);
        }
    }
}