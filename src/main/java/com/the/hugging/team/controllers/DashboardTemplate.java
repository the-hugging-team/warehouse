package com.the.hugging.team.controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class DashboardTemplate {
    @FXML
    private Pane topbar;

    @FXML
    private Pane profile;

    @FXML
    private VBox menu;

    @FXML
    private Button products;

    @FXML
    private Button productsArrow;

    @FXML
    private Button reports;

    @FXML
    private Button reportsArrow;

    @FXML
    public void initialize() {

    }

    public void productsClick(ActionEvent e) {
        boolean isActive = products.getParent().getStyleClass().contains("menu-button-dropdown-active");

        int productsIndex = menu.getChildren().indexOf(products.getParent());

        if (isActive) {
            products.getParent().getStyleClass().remove("menu-button-dropdown-active");
            ((FontAwesomeIconView) productsArrow.getGraphic()).setIcon(FontAwesomeIcon.ANGLE_DOWN);
            menu.getChildren().remove(productsIndex + 1, productsIndex + 4);
        } else {
            if(reports.getParent().getStyleClass().contains("menu-button-dropdown-active")) {
                reportsClick(e);
                productsIndex = menu.getChildren().indexOf(products.getParent());
            }

            products.getParent().getStyleClass().add("menu-button-dropdown-active");
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
    }

    public void reportsClick(ActionEvent e) {
        boolean isActive = reports.getParent().getStyleClass().contains("menu-button-dropdown-active");

        int reportsIndex = menu.getChildren().indexOf(reports.getParent());

        if (isActive) {
            reports.getParent().getStyleClass().remove("menu-button-dropdown-active");
            ((FontAwesomeIconView) reportsArrow.getGraphic()).setIcon(FontAwesomeIcon.ANGLE_DOWN);
            menu.getChildren().remove(reportsIndex + 1, reportsIndex + 3);
        } else {
            if(products.getParent().getStyleClass().contains("menu-button-dropdown-active")) {
                productsClick(e);
                reportsIndex = menu.getChildren().indexOf(reports.getParent());
            }

            reports.getParent().getStyleClass().add("menu-button-dropdown-active");
            ((FontAwesomeIconView) reportsArrow.getGraphic()).setIcon(FontAwesomeIcon.ANGLE_UP);

            Button money = new Button("Money");
            Button user = new Button("User");

            money.getStyleClass().addAll("menu-button-dropdown-item", "font-regular");
            user.getStyleClass().addAll("menu-button-dropdown-item", "font-regular");

            menu.getChildren().add(reportsIndex + 1, money);
            menu.getChildren().add(reportsIndex + 2, user);
        }
    }
}
