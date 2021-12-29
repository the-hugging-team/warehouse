package com.the.hugging.team.controllers;

import com.the.hugging.team.entities.Notification;
import com.the.hugging.team.entities.User;
import com.the.hugging.team.services.NotificationService;
import com.the.hugging.team.utils.Session;
import com.the.hugging.team.utils.Window;
import com.the.hugging.team.utils.WindowHandler;
import com.the.hugging.team.utils.wizard.beans.PaymentBean;
import com.the.hugging.team.utils.wizard.events.EventSource;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.List;

public class DashboardTemplate extends WindowHandler {

    private static DashboardTemplate instance = null;
    private final Session session = Session.getInstance();
    private final User user = session.getUser();
    private final NotificationService notificationService = NotificationService.getInstance();

    private ObservableList<Notification> notifications;

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
    private Button companiesButton;
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

    @FXML
    private Button notificationsButton;

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

        Platform.runLater(() -> {
            homeClick(null);

            initNotifications();
        });

        FontAwesomeIconView usersIcon = new FontAwesomeIconView(FontAwesomeIcon.USERS);
        usersIcon.setSize("12.0pt");
        usersIcon.getStyleClass().add("menu-icon");
        usersButton = new Button("Users");
        usersButton.setGraphic(usersIcon);
        usersButton.setGraphicTextGap(6);
        usersButton.getStyleClass().addAll("menu-button-dropdown-item", "font-regular");
        usersButton.setOnAction(this::usersClick);


        FontAwesomeIconView companiesIcon = new FontAwesomeIconView(FontAwesomeIcon.TRUCK);
        companiesIcon.setSize("12.0pt");
        companiesIcon.getStyleClass().add("menu-icon");
        companiesButton = new Button("Companies");
        companiesButton.setGraphic(companiesIcon);
        companiesButton.setGraphicTextGap(6);
        companiesButton.getStyleClass().addAll("menu-button-dropdown-item", "font-regular");
        companiesButton.setOnAction(this::companiesClick);


        FontAwesomeIconView cashRegistersIcon = new FontAwesomeIconView(FontAwesomeIcon.MONEY);
        cashRegistersIcon.setSize("12.0pt");
        cashRegistersIcon.getStyleClass().add("menu-icon");
        cashRegistersButton = new Button("Cash Registers");
        cashRegistersButton.setGraphic(cashRegistersIcon);
        cashRegistersButton.setGraphicTextGap(6);
        cashRegistersButton.getStyleClass().addAll("menu-button-dropdown-item", "font-regular");
        cashRegistersButton.setOnAction(this::cashRegistersClick);
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
        menu.getChildren().add(manageButtonIndex + 2, companiesButton);
        menu.getChildren().add(manageButtonIndex + 3, cashRegistersButton);
    }

    private void closeManageDropdown() {
        int manageButtonIndex = menu.getChildren().indexOf(manageButton.getParent());
        manageButton.getParent().getStyleClass().remove("menu-button-dropdown-active");
        ((FontAwesomeIconView) manageArrow.getGraphic()).setIcon(FontAwesomeIcon.ANGLE_DOWN);
        menu.getChildren().remove(manageButtonIndex + 1, manageButtonIndex + 4);
    }

    public void usersClick(ActionEvent event) {
        selectButton(usersButton);

        loadView("views/dashboard/cruds/users-crud.fxml");
    }

    public void companiesClick(ActionEvent e) {
        selectButton(companiesButton);

        loadView("views/dashboard/cruds/companies-crud.fxml");
    }

    public void cashRegistersClick(ActionEvent e) {
        selectButton(cashRegistersButton);

        loadView("views/dashboard/cruds/cash-registers-crud.fxml");
    }
    // Dropdown menu end

    @FXML
    public void sellClick(ActionEvent event) {
        selectButton(sellButton);

        loadView("views/dashboard/wizards/sell.fxml");
    }

    @FXML
    public void deliveriesClick(ActionEvent event) {
        selectButton(deliveriesButton);

        loadView("views/dashboard/wizards/delivery.fxml");
    }

    @FXML
    public void storageClick(ActionEvent event) {
        selectButton(storageButton);

        loadView("views/dashboard/cruds/rooms-crud.fxml");
    }

    @FXML
    public void inventoryClick(ActionEvent event) {
        selectButton(inventoryButton);

        loadView("views/dashboard/cruds/inventory-crud.fxml");
    }

    @FXML
    public void reportsClick(ActionEvent e) {
        selectButton(reportsButton);
    }

    public void logout(ActionEvent e) {
        session.cleanSession();

        Window loginWindow = new Window("views/login/login.fxml");
        loginWindow.setAsNextStage(e);
        loginWindow.showStage();
    }

//    ----------Utility Methods----------

    private void initNotifications() {
        Thread notificationThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    List<Notification> notificationsList = notificationService.getUnreadUserNotifications(user);
                    if (notificationsList.size() > 0) {
                        notifications.setAll(notificationsList);
                    }
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        notificationThread.setDaemon(true);
        notificationThread.start();

        notifications.addListener((ListChangeListener<Notification>) c -> {
            if (notifications.size() > 0) {
                notificationsButton.getStyleClass().add("menu-button-notifications-active");
            } else {
                notificationsButton.getStyleClass().remove("menu-button-notifications-active");
            }
        });
    }

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
        if (!user.can("permissions.companies.index")) {
            menu.getChildren().remove(companiesButton);
        }
        if (!user.can("permissions.rooms.index")) {
            menu.getChildren().remove(storageButton);
        }
        if (!user.can("permissions.cash-registers.index")) {
            menu.getChildren().remove(cashRegistersButton);
        }
        if (!user.can("permissions.products.index")) {
            menu.getChildren().remove(inventoryButton);
        }
    }

    protected void loadView(String view) {
        PaymentBean.clear();
        EventSource.clear();

        Window window = new Window(view);
        window.setAsAnchorPane(workspace, this.getWindow());
    }
}
