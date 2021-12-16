package com.the.hugging.team.utils.wizard.beans;

import com.the.hugging.team.entities.Product;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SellBean {
    private static SellBean instance;
    private ObservableList<Product> productsData;

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
