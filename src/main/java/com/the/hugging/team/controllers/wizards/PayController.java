package com.the.hugging.team.controllers.wizards;

import com.the.hugging.team.entities.Product;
import com.the.hugging.team.services.*;
import com.the.hugging.team.utils.WindowHandler;
import com.the.hugging.team.utils.wizard.beans.PaymentBean;
import com.the.hugging.team.utils.wizard.events.EventSource;
import com.the.hugging.team.utils.wizard.events.EventType;
import com.the.hugging.team.utils.wizard.events.SetCurrentStepEvent;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class PayController extends WindowHandler {
    private final SaleService saleService = SaleService.getInstance();
    private final DeliveryService deliveryService = DeliveryService.getInstance();
    private final ProductService productService = ProductService.getInstance();
    private final ActivityService activityService = ActivityService.getInstance();
    private final ActivityTypeService activityTypeService = ActivityTypeService.getInstance();
    private final EventSource eventSource = EventSource.getInstance();

    private final PaymentBean paymentBean = PaymentBean.getInstance();
    private boolean isSell, isDelivery;

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
        isSell = paymentBean.getBeanType().equals(PaymentBean.BeanType.SELL);
        isDelivery = paymentBean.getBeanType().equals(PaymentBean.BeanType.DELIVERY);

        payAnchor.widthProperty().addListener(
                (observableValue, oldAnchorWidth, newAnchorWidth) -> paymentInformationPane.setLayoutX((newAnchorWidth.doubleValue() / 2) - (paymentInformationPane.getPrefWidth() / 2)));
        payAnchor.heightProperty().addListener(
                (observableValue, oldAnchorHeight, newAnchorHeight) -> paymentInformationPane.setLayoutY((newAnchorHeight.doubleValue() / 2) - (paymentInformationPane.getPrefHeight() / 2)));

        products = paymentBean.getProductsData();

        if (isSell) {
            if (paymentBean.getInvoice() == null) {
                basePrice = products.stream().map(Product::getRetailPrice).reduce(0.00, Double::sum);
            } else {
                basePrice = products.stream().map(Product::getWholesalePrice).reduce(0.00, Double::sum);
            }
        } else if (isDelivery) {
            basePrice = products.stream().map(Product::getDeliveryPrice).reduce(0.00, Double::sum);
        }

        if (basePrice > 0) {
            dds = 20;
        }

        if (isSell) {
            ddsValue = basePrice * dds / 100;
            finalPrice = basePrice + ddsValue;
            ddsPercentageField.setText(dds.toString());
        } else if (isDelivery) {
            ddsValue = 0.0;
            finalPrice = basePrice;
            ddsPercentageField.setText("0");
        }

        basePriceField.setText(basePrice.toString());
        ddsValueField.setText(ddsValue.toString());
        finalPriceField.setText(finalPrice.toString());

        paymentBean.setProductsPrice(basePrice);
        paymentBean.setProductsDdsValue(ddsValue);
        paymentBean.setProductsFinalPrice(finalPrice);
    }

    @FXML
    public void paymentButtonClick(ActionEvent event) {
        if (paymentBean.getInvoice() != null) {
            paymentBean.getInvoice().setBasePrice(basePrice);
            paymentBean.getInvoice().setDds(ddsValue);
            paymentBean.getInvoice().setTotalPrice(finalPrice);
        }

        if (isSell) {
            saleService.addSaleFromBean(paymentBean, finalPrice);
            productService.updateProductsFromSellBean(paymentBean.getSearchData());
            activityService.addActivity(activityTypeService.getActivityTypeBySlug("activity-types.sale"));
        } else if (isDelivery) {
            deliveryService.addDeliveryFromBean(paymentBean, finalPrice);
            productService.updateProductsFromDeliveryBean(paymentBean.getProductsData());
            activityService.addActivity(activityTypeService.getActivityTypeBySlug("activity-types.delivery"));
        }

        PaymentBean.reset();
        eventSource.fire(EventType.SET_CURRENT_STEP_EVENT_TYPE, new SetCurrentStepEvent(1));
    }
}
