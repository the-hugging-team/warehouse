package com.the.hugging.team.controllers;

import com.the.hugging.team.entities.User;
import com.the.hugging.team.utils.Session;
import com.the.hugging.team.utils.Window;
import com.the.hugging.team.utils.WindowHandler;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class DashboardTemplate extends WindowHandler {

    private static DashboardTemplate instance = null;
    private final Session session = Session.getInstance();
    protected final User user = session.getUser();
    @FXML
    private AnchorPane workspace;
    @FXML
    private Pane profile;
    @FXML
    private VBox menu;
    @FXML
    private Button homeButton;
    @FXML
    private Button manageButton;
    @FXML
    private Button manageArrow;
    private Button usersButton;
    private Button clientsButton;
    private Button suppliersButton;
    private Button cashRegistersButton;
    @FXML
    private Button sellButton;
    @FXML
    private Button deliveriesButton;
    @FXML
    private Button storageButton;
    @FXML
    private Button inventoryButton;
    @FXML
    private Button reportsButton;

    public static DashboardTemplate getInstance() {
        return instance;
    }

    @FXML
    public void initialize() {
        instance = this;

        if (user != null) {
            ((Label) profile.lookup("#userNames")).setText(user.getFirstName() + " " + user.getLastName());
            ((Label) profile.lookup("#role")).setText(user.getRole().getName());

            checkMenuPermissions();
        } else {
            ((Label) profile.lookup("#userNames")).setText("");
            ((Label) profile.lookup("#role")).setText("");
        }

        Platform.runLater(() -> homeClick(null));

        FontAwesomeIconView usersIcon = new FontAwesomeIconView(FontAwesomeIcon.USERS);
        usersIcon.setSize("12.0pt");
        usersIcon.getStyleClass().add("menu-icon");
        usersButton = new Button("Users");
        usersButton.setGraphic(usersIcon);
        usersButton.setGraphicTextGap(6);
        usersButton.getStyleClass().addAll("menu-button-dropdown-item", "font-regular");
        usersButton.setOnAction(this::usersClick);


        FontAwesomeIconView clientsIcon = new FontAwesomeIconView(FontAwesomeIcon.BRIEFCASE);
        clientsIcon.setSize("12.0pt");
        clientsIcon.getStyleClass().add("menu-icon");
        clientsButton = new Button("Clients");
        clientsButton.setGraphic(clientsIcon);
        clientsButton.setGraphicTextGap(6);
        clientsButton.getStyleClass().addAll("menu-button-dropdown-item", "font-regular");
        clientsButton.setOnAction(this::clientsClick);


        FontAwesomeIconView suppliersIcon = new FontAwesomeIconView(FontAwesomeIcon.TRUCK);
        suppliersIcon.setSize("12.0pt");
        suppliersIcon.getStyleClass().add("menu-icon");
        suppliersButton = new Button("Suppliers");
        suppliersButton.setGraphic(suppliersIcon);
        suppliersButton.setGraphicTextGap(6);
        suppliersButton.getStyleClass().addAll("menu-button-dropdown-item", "font-regular");
        suppliersButton.setOnAction(this::suppliersClick);


        FontAwesomeIconView cashRegistersIcon = new FontAwesomeIconView(FontAwesomeIcon.MONEY);
        cashRegistersIcon.setSize("12.0pt");
        cashRegistersIcon.getStyleClass().add("menu-icon");
        cashRegistersButton = new Button("Cash Registers");
        cashRegistersButton.setGraphic(cashRegistersIcon);
        cashRegistersButton.setGraphicTextGap(6);
        cashRegistersButton.getStyleClass().addAll("menu-button-dropdown-item", "font-regular");
//        cashRegistersButton.setOnAction(this::cashRegistersClick);
    }

    @FXML
    public void homeClick(ActionEvent event) {
        selectButton(homeButton);

        loadView("views/dashboard/home.fxml");
    }

    // Dropdown menu start
    @FXML
    public void manageClick(ActionEvent e) {
        boolean isActive = manageButton.getParent().getStyleClass().contains("menu-button-dropdown-active");


        if (isActive) {
            closeManageDropdown();
        } else {
            openManageDropdown();
        }
    }

    private void openManageDropdown() {
        int manageButtonIndex = menu.getChildren().indexOf(manageButton.getParent());
        manageButton.getParent().getStyleClass().add("menu-button-dropdown-active");
        ((FontAwesomeIconView) manageArrow.getGraphic()).setIcon(FontAwesomeIcon.ANGLE_UP);

        menu.getChildren().add(manageButtonIndex + 1, usersButton);
        menu.getChildren().add(manageButtonIndex + 2, clientsButton);
        menu.getChildren().add(manageButtonIndex + 3, suppliersButton);
        menu.getChildren().add(manageButtonIndex + 4, cashRegistersButton);
    }

    private void closeManageDropdown() {
        int manageButtonIndex = menu.getChildren().indexOf(manageButton.getParent());
        manageButton.getParent().getStyleClass().remove("menu-button-dropdown-active");
        ((FontAwesomeIconView) manageArrow.getGraphic()).setIcon(FontAwesomeIcon.ANGLE_DOWN);
        menu.getChildren().remove(manageButtonIndex + 1, manageButtonIndex + 5);
    }

    public void usersClick(ActionEvent event) {
        selectButton(usersButton);

        loadView("views/dashboard/cruds/users-crud.fxml");
    }

    public void clientsClick(ActionEvent e) {
        selectButton(clientsButton);

        loadView("views/dashboard/cruds/clients-crud.fxml");
    }

    public void suppliersClick(ActionEvent e) {
        selectButton(suppliersButton);

        loadView("views/dashboard/cruds/suppliers-crud.fxml");
    }
    // Dropdown menu end

    @FXML
    public void sellClick(ActionEvent event) {
        selectButton(sellButton);
    }

    @FXML
    public void deliveriesClick(ActionEvent event) {
        selectButton(deliveriesButton);
    }

    @FXML
    public void storageClick(ActionEvent event) {
        selectButton(storageButton);

        loadView("views/dashboard/cruds/rooms-crud.fxml");
    }

    @FXML
    public void inventoryClick(ActionEvent event) {
        selectButton(inventoryButton);
    }

    @FXML
    public void reportsClick(ActionEvent e) {

    }

    public void logout(ActionEvent e) {
        session.cleanSession();

        Window loginWindow = new Window("views/login/login.fxml");
        loginWindow.setAsNextStage(e);
        loginWindow.showStage();
    }

//    ----------Utility Methods----------

    private void selectButton(Button button) {
        boolean isDropdownItem = button.getStyleClass().contains("menu-button-dropdown-item");

        for (Node node : menu.getChildren()) {
            if (node instanceof Button) {
                node.getStyleClass().remove("menu-button-active");
            }

            if (node instanceof HBox && !isDropdownItem) {
                if (node.getStyleClass().contains("menu-button-dropdown-active")) {
                    Platform.runLater(this::closeManageDropdown);
                }
            }
        }

        button.getStyleClass().add("menu-button-active");
    }

    private void checkMenuPermissions() {
        if (!user.can("permissions.users.index")) {
            menu.getChildren().remove(usersButton);
        }
        if (!user.can("permissions.clients.index")) {
            menu.getChildren().remove(clientsButton);
        }
        if (!user.can("permissions.suppliers.index")) {
            menu.getChildren().remove(suppliersButton);
        }
        if (!user.can("permissions.rooms.index")) {
            menu.getChildren().remove(storageButton);
        }
    }

    protected void loadView(String view) {
        Window window = new Window(view);
        window.setAsAnchorPane(workspace, this.getWindow());
    }
}
