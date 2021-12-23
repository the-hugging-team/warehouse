package com.the.hugging.team.controllers.wizards;

import com.the.hugging.team.entities.Product;
import com.the.hugging.team.services.ProductService;
import com.the.hugging.team.services.SaleService;
import com.the.hugging.team.utils.WindowHandler;
import com.the.hugging.team.utils.wizard.beans.SellBean;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class PayController extends WindowHandler {
    private final SaleService saleService = SaleService.getInstance();
    private final ProductService productService = ProductService.getInstance();

    private final SellBean sellBean = SellBean.getInstance();

    @FXML
    private Button paymentButton;

    @FXML
    private TextField basePriceField;
    @FXML
    private TextField ddsPercentageField;
    @FXML
    private TextField ddsValueField;
    @FXML
    private TextField finalPriceField;
    @FXML
    private AnchorPane payAnchor;
    @FXML
    private Pane paymentInformationPane;

    private ObservableList<Product> products;
    private Integer dds = 0;
    private Double basePrice = 0.0;
    private Double ddsValue = 0.0;
    private Double finalPrice = 0.0;

    @FXML
    private void initialize() {
        payAnchor.widthProperty().addListener(
                (observableValue, oldAnchorWidth, newAnchorWidth) -> paymentInformationPane.setLayoutX((newAnchorWidth.doubleValue() / 2) - (paymentInformationPane.getPrefWidth() / 2)));
        payAnchor.heightProperty().addListener(
                (observableValue, oldAnchorHeight, newAnchorHeight) -> paymentInformationPane.setLayoutY((newAnchorHeight.doubleValue() / 2) - (paymentInformationPane.getPrefHeight() / 2)));

        products = sellBean.getProductsData();

        if (sellBean.getBuyerCompany() == null)
            basePrice = products.stream().map(Product::getRetailPrice).reduce(0.00, Double::sum);
        else
            basePrice = products.stream().map(Product::getWholesalePrice).reduce(0.00, Double::sum);

        if (basePrice > 0) dds = 20;
        ddsValue = basePrice * dds / 100;
        finalPrice = basePrice + ddsValue;

        basePriceField.setText(basePrice.toString());
        if (sellBean.getBuyerCompany() == null)
            ddsPercentageField.setText(dds.toString());
        ddsValueField.setText(ddsValue.toString());
        finalPriceField.setText(finalPrice.toString());

        sellBean.setProductsPrice(basePrice);
        sellBean.setProductsDdsValue(ddsValue);
        sellBean.setProductsFinalPrice(finalPrice);
    }

    @FXML
    public void paymentButtonClick(ActionEvent event) {
        saleService.addSaleFromBean(sellBean, finalPrice);
        productService.updateProductsFromSellBean(sellBean.getSearchData());
    }
}
