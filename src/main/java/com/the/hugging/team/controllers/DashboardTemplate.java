package com.the.hugging.team.controllers;

import com.the.hugging.team.entities.User;
import com.the.hugging.team.utils.ICallsBack;
import com.the.hugging.team.utils.Session;
import com.the.hugging.team.utils.Window;
import com.the.hugging.team.utils.WindowHandler;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class DashboardTemplate extends WindowHandler implements ICallsBack {

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
    private Button usersButton;

    @FXML
    private Button clientsButton;

    @FXML
    private Button suppliersButton;

    @FXML
    private Button cashRegistersButton;

    @FXML
    private Button storageButton;

    @FXML
    private Button productsButton;

    @FXML
    private Button productsArrow;

    @FXML
    private Button reportsButton;

    @FXML
    private Button reportsArrow;

    @FXML
    public void initialize() {
        if (user != null) {
            ((Label) profile.lookup("#userNames")).setText(user.getFirstName() + " " + user.getLastName());
            ((Label) profile.lookup("#role")).setText(user.getRole().getName());

            checkMenuPermissions();
        } else {
            ((Label) profile.lookup("#userNames")).setText("");
            ((Label) profile.lookup("#role")).setText("");
        }
    }

    @FXML
    public void homeClick(ActionEvent event) {
        selectButton(homeButton);

        Window homeWindow = new Window("views/dashboard/home.fxml");
        homeWindow.setAsAnchorPane(workspace, this.getWindow());
    }

    @FXML
    public void usersClick(ActionEvent event) {
        selectButton(usersButton);

        Window usersWindow = new Window("views/dashboard/cruds/users-crud.fxml");
        usersWindow.setAsAnchorPane(workspace, this.getWindow());
    }

    @FXML
    public void clientsClick(ActionEvent e) {
        selectButton(clientsButton);

        Window home = new Window("views/dashboard/cruds/clients-crud.fxml");
        home.setAsAnchorPane(workspace, this.getWindow());
    }

    public void suppliersClick(ActionEvent e) {
        selectButton(suppliersButton);

        Window home = new Window("views/dashboard/cruds/suppliers-crud.fxml");
        home.setAsAnchorPane(workspace, this.getWindow());
    }

    public void productsClick(ActionEvent e) {
        boolean isActive = productsButton.getParent().getStyleClass().contains("menu-button-dropdown-active");

        int productsIndex = menu.getChildren().indexOf(productsButton.getParent());

        if (isActive) {
            productsButton.getParent().getStyleClass().remove("menu-button-dropdown-active");
            ((FontAwesomeIconView) productsArrow.getGraphic()).setIcon(FontAwesomeIcon.ANGLE_DOWN);
            menu.getChildren().remove(productsIndex + 1, productsIndex + 4);
        } else {
            selectButton(productsButton);
            productsIndex = menu.getChildren().indexOf(productsButton.getParent());

            productsButton.getParent().getStyleClass().add("menu-button-dropdown-active");
            ((FontAwesomeIconView) productsArrow.getGraphic()).setIcon(FontAwesomeIcon.ANGLE_UP);

            Button stock = new Button("Stock");
            Button sell = new Button("Sell");
            Button delivery = new Button("Delivery");

            stock.getStyleClass().addAll("menu-button-dropdown-item", "font-regular");
            sell.getStyleClass().addAll("menu-button-dropdown-item", "font-regular");
            delivery.getStyleClass().addAll("menu-button-dropdown-item", "font-regular");

            menu.getChildren().add(productsIndex + 1, stock);
            menu.getChildren().add(productsIndex + 2, sell);
            menu.getChildren().add(productsIndex + 3, delivery);
        }

        Window home = new Window("views/dashboard/test-template-crud.fxml");
        home.setAsAnchorPane(workspace, this.getWindow());
    }

    @FXML
    public void reportsClick(ActionEvent e) {
        boolean isActive = reportsButton.getParent().getStyleClass().contains("menu-button-dropdown-active");

        int reportsIndex = menu.getChildren().indexOf(reportsButton.getParent());

        if (isActive) {
            reportsButton.getParent().getStyleClass().remove("menu-button-dropdown-active");
            ((FontAwesomeIconView) reportsArrow.getGraphic()).setIcon(FontAwesomeIcon.ANGLE_DOWN);
            menu.getChildren().remove(reportsIndex + 1, reportsIndex + 3);
        } else {
            selectButton(reportsButton);
            reportsIndex = menu.getChildren().indexOf(reportsButton.getParent());

            reportsButton.getParent().getStyleClass().add("menu-button-dropdown-active");
            ((FontAwesomeIconView) reportsArrow.getGraphic()).setIcon(FontAwesomeIcon.ANGLE_UP);

            Button money = new Button("Money");
            Button user = new Button("User");

            money.getStyleClass().addAll("menu-button-dropdown-item", "font-regular");
            user.getStyleClass().addAll("menu-button-dropdown-item", "font-regular");

            menu.getChildren().add(reportsIndex + 1, money);
            menu.getChildren().add(reportsIndex + 2, user);
        }
    }

    public void logout(ActionEvent e) {
        session.cleanSession();

        Window loginWindow = new Window("views/login/login.fxml");
        loginWindow.setAsNextStage(e);
        loginWindow.showStage();
    }

//    ----------Utility Methods----------

    private void selectButton(Button button) {
        for (Node node : menu.getChildren()) {
            if (node instanceof Button) {
                node.getStyleClass().remove("menu-button-active");
            }

            if (node instanceof HBox dropdown) {
                if (dropdown.getStyleClass().contains("menu-button-dropdown-active")) {
                    for (Node dropdownNode : dropdown.getChildren()) {
                        if (dropdownNode instanceof Button) {
                            if (dropdownNode == productsButton) {
                                productsClick(null);
                            } else if (dropdownNode == reportsButton) {
                                reportsClick(null);
                            }
                        }
                    }
                }
            }
        }

        if (button == productsButton || button == reportsButton) {
            button.getParent().getStyleClass().add("menu-button-dropdown-active");
        } else {
            button.getStyleClass().add("menu-button-active");
        }
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
    }

    @Override
    public void callBack(Object... objects) {
        homeClick(null);
    }
}
