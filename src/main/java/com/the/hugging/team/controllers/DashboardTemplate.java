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
    private Button reports;

    @FXML
    public void initialize() {

    }

    public void productsClick(ActionEvent e) {
        boolean isActive = products.getStyleClass().contains("menu-button-dropdown-active");

        int productsIndex = menu.getChildren().indexOf(products);

        if (isActive) {
            products.getStyleClass().remove("menu-button-dropdown-active");
//            ((FontAwesomeIconView) products.getGraphic()).setIcon(FontAwesomeIcon.ANGLE_DOWN);
            menu.getChildren().remove(productsIndex + 1, productsIndex + 4);
        } else {
            if(reports.getStyleClass().contains("menu-button-dropdown-active")) {
                reportsClick(e);
                productsIndex = menu.getChildren().indexOf(products);
            }

            products.getStyleClass().add("menu-button-dropdown-active");
//            ((FontAwesomeIconView) products.getGraphic()).setIcon(FontAwesomeIcon.ANGLE_UP);

            Button stock = new Button("Stock");
            Button sell = new Button("Sell");
            Button delivery = new Button("Delivery");

            stock.getStyleClass().addAll("menu-button-dropdown", "font-regular");
            sell.getStyleClass().addAll("menu-button-dropdown", "font-regular");
            delivery.getStyleClass().addAll("menu-button-dropdown", "font-regular");

            menu.getChildren().add(productsIndex + 1, stock);
            menu.getChildren().add(productsIndex + 2, sell);
            menu.getChildren().add(productsIndex + 3, delivery);
        }
    }

    public void reportsClick(ActionEvent e) {
        boolean isActive = reports.getStyleClass().contains("menu-button-dropdown-active");

        int reportsIndex = menu.getChildren().indexOf(reports);

        if (isActive) {
            reports.getStyleClass().remove("menu-button-dropdown-active");
            menu.getChildren().remove(reportsIndex + 1, reportsIndex + 3);
        } else {
            if(products.getStyleClass().contains("menu-button-dropdown-active")) {
                productsClick(e);
                reportsIndex = menu.getChildren().indexOf(reports);
            }

            reports.getStyleClass().add("menu-button-dropdown-active");

            Button money = new Button("Money");
            Button user = new Button("User");

            money.getStyleClass().addAll("menu-button-dropdown", "font-regular");
            user.getStyleClass().addAll("menu-button-dropdown", "font-regular");

            menu.getChildren().add(reportsIndex + 1, money);
            menu.getChildren().add(reportsIndex + 2, user);
        }
    }
}
