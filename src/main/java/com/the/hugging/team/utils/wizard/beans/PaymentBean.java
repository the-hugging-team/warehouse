package com.the.hugging.team.utils.wizard.beans;

import com.the.hugging.team.entities.CashRegister;
import com.the.hugging.team.entities.Invoice;
import com.the.hugging.team.entities.Product;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentBean {
    private static PaymentBean instance;
    private ObservableList<Product> searchData;
    private ObservableList<Product> productsData;
    private Double productsPrice;
    private Double productsDdsValue;
    private Double productsFinalPrice;
    private Invoice invoice;
    private CashRegister cashRegister;
    private BeanType beanType;

    public static PaymentBean getInstance() {
        if (instance == null) {
            instance = new PaymentBean();
        }
        return instance;
    }

    public static void clear() {
        instance = null;
    }

    public static void reset() {
        BeanType beanType = instance.getBeanType();
        instance = new PaymentBean();
        instance.setBeanType(beanType);
    }

    public enum BeanType {
        SELL,
        DELIVERY,
    }
}
