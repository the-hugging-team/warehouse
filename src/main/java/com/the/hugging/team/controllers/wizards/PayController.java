package com.the.hugging.team.controllers.wizards;

import com.the.hugging.team.entities.Product;
import com.the.hugging.team.services.ProductService;
import com.the.hugging.team.services.SaleService;
import com.the.hugging.team.services.TransactionService;
import com.the.hugging.team.services.TransactionTypeService;
import com.the.hugging.team.utils.Session;
import com.the.hugging.team.utils.WindowHandler;
import com.the.hugging.team.utils.wizard.beans.SellBean;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class PayController extends WindowHandler {
    private final SaleService saleService = SaleService.getInstance();
    private final ProductService productService = ProductService.getInstance();

    private final SellBean sellBean = SellBean.getInstance();

    @FXML
    private TextField basePriceField;
    @FXML
    private TextField ddsPercentageField;
    @FXML
    private TextField ddsValueField;
    @FXML
    private TextField finalPriceField;

    private ObservableList<Product> products;
    private Integer dds;
    private Double basePrice;
    private Double ddsValue;
    private Double finalPrice;

    @FXML
    private void initialize() {
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
        saleService.addSaleByBean(sellBean, finalPrice);
        productService.updateProductsBySellBean(sellBean);
    }
}
