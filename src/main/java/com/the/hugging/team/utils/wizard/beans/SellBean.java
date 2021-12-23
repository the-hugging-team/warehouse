package com.the.hugging.team.utils.wizard.beans;

import com.the.hugging.team.entities.CashRegister;
import com.the.hugging.team.entities.Company;
import com.the.hugging.team.entities.Invoice;
import com.the.hugging.team.entities.Product;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SellBean {
    private static SellBean instance;
    private ObservableList<Product> searchData;
    private ObservableList<Product> productsData;
    private Double productsPrice;
    private Double productsDdsValue;
    private Double productsFinalPrice;
    private Company buyerCompany;
    private Invoice invoice;
    private CashRegister cashRegister;

    public static SellBean getInstance() {
        if (instance == null) {
            instance = new SellBean();
        }
        return instance;
    }

    public static void clear() {
        instance = null;
    }
}
