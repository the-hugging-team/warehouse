package com.the.hugging.team.controllers.wizards;

import com.the.hugging.team.entities.Product;
import com.the.hugging.team.entities.ProductCategory;
import com.the.hugging.team.services.ProductCategoryService;
import com.the.hugging.team.services.ProductService;
import com.the.hugging.team.utils.Dialogs;
import com.the.hugging.team.utils.TableResizer;
import com.the.hugging.team.utils.WindowHandler;
import com.the.hugging.team.utils.wizard.beans.PaymentBean;
import com.the.hugging.team.utils.wizard.events.EventSource;
import com.the.hugging.team.utils.wizard.events.EventType;
import com.the.hugging.team.utils.wizard.events.NextStepEvent;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

public class SelectProductsController extends WindowHandler {

    private final ProductCategoryService productCategoryService = ProductCategoryService.getInstance();
    private final ProductService productService = ProductService.getInstance();
    private final PaymentBean paymentBean = PaymentBean.getInstance();
    private final EventSource eventSource = EventSource.getInstance();

    @FXML
    private AnchorPane wizardStepPane;

    @FXML
    private TextField searchField;
    @FXML
    private ChoiceBox<ProductCategory> productCategories;

    @FXML
    private TableView<Product> searchTable;
    private TableColumn<Product, String> searchNomenclature;
    private TableColumn<Product, String> searchName;
    private TableColumn<Product, Double> searchQuantity;
    private TableColumn<Product, String> searchQuantityType;
    private TableColumn<Product, Double> searchRetailPrice;
    private TableColumn<Product, Double> searchWholesalePrice;
    private TableColumn<Product, Double> searchDeliveryPrice;

    private ObservableList<Product> searchData;
    private FilteredList<Product> searchFilteredList;

    @FXML
    private TableView<Product> productsTable;
    private TableColumn<Product, String> productsNomenclature;
    private TableColumn<Product, String> productsName;
    private TableColumn<Product, Double> productsQuantity;
    private TableColumn<Product, String> productsQuantityType;
    private TableColumn<Product, Double> productsRetailPrice;
    private TableColumn<Product, Double> productsRetailDDS;
    private TableColumn<Product, Double> productsTotalRetailPrice;
    private TableColumn<Product, Double> productsWholesalePrice;
    private TableColumn<Product, Double> productsWholesaleDDS;
    private TableColumn<Product, Double> productsTotalWholesalePrice;
    private TableColumn<Product, Double> productsDeliveryPrice;

    private ObservableList<Product> productsData;

    @FXML
    private void initialize() {
        setupTables();
        setupCategories();
    }

    @FXML
    public void search(ActionEvent e) {
        String searchText = searchField.getText();
        if (searchText.isEmpty()) {
            searchFilteredList.setPredicate(p -> true);
        } else {
            searchFilteredList.setPredicate(p ->
                    p.getName().toLowerCase().contains(searchText.toLowerCase()) ||
                            p.getNomenclature().toLowerCase().contains(searchText.toLowerCase())
            );
        }
    }

    @FXML
    public void nextStep(ActionEvent e) {
        eventSource.fire(EventType.NEXT_STEP_EVENT_TYPE, new NextStepEvent());
    }

    private void setupTables() {
        // Search table
        createColumns();
        setupColumns(searchNomenclature, searchName, searchQuantity, searchQuantityType, searchRetailPrice, searchWholesalePrice);

        TableResizer.setDefault(searchTable);
        searchTable.setRowFactory(tv -> {
            final TableRow<Product> row = new TableRow<>();
            final ContextMenu rowMenu = getAddContextMenu();

            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(rowMenu)
            );

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    addToProductsTable();
                }
            });

            return row;
        });

        // Products table
        if (paymentBean.getBeanType().equals(PaymentBean.BeanType.SELL)) {
            createSellColumns();
            setupSellColumns(productsNomenclature, productsName, productsQuantity, productsQuantityType, productsRetailPrice, productsRetailDDS, productsTotalRetailPrice, productsWholesalePrice, productsWholesaleDDS, productsTotalWholesalePrice);
            TableResizer.setCustomColumns(productsTable, List.of(0, 1, 2, 3, 9), List.of(100, 150, 75, 55, 150));
        } else if (paymentBean.getBeanType().equals(PaymentBean.BeanType.DELIVERY)) {
            createDeliveryColumns();
            setupDeliveryColumns(productsNomenclature, productsName, productsQuantity, productsQuantityType, productsDeliveryPrice);
            TableResizer.setDefault(productsTable);
        }

        productsTable.setRowFactory(tv -> {
            final TableRow<Product> row = new TableRow<>();
            final ContextMenu rowMenu = getEditDeleteContextMenu();

            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(rowMenu)
            );

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    editProduct();
                }
            });

            return row;
        });

        wizardStepPane.heightProperty().addListener((observable, oldValue, newValue) -> {
            double newTableHeight = (newValue.doubleValue() - 70) / 2;
            searchTable.setPrefHeight(newTableHeight);
            productsTable.setPrefHeight(newTableHeight);
            productsTable.setLayoutY((newValue.doubleValue() / 2) + 5);
        });
    }

    private void setupCommonColumns(TableColumn<Product, String> nomenclature, TableColumn<Product, String> name, TableColumn<Product, Double> quantity, TableColumn<Product, String> quantityType) {

        nomenclature.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomenclature()));
        name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        quantity.setCellValueFactory(cellData -> new SimpleDoubleProperty(BigDecimal.valueOf(cellData.getValue().getQuantity()).setScale(2, RoundingMode.HALF_UP).doubleValue()).asObject());
        quantityType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductQuantityType().getName()));
    }

    private void setupColumns(TableColumn<Product, String> nomenclature, TableColumn<Product, String> name, TableColumn<Product, Double> quantity, TableColumn<Product, String> quantityType, TableColumn<Product, Double> retailPrice, TableColumn<Product, Double> wholesalePrice) {
        setupCommonColumns(nomenclature, name, quantity, quantityType);


        if (paymentBean.getBeanType().equals(PaymentBean.BeanType.SELL)) {
            retailPrice.setCellValueFactory(cellData -> new SimpleDoubleProperty(BigDecimal.valueOf(cellData.getValue().getRetailPrice()).setScale(2, RoundingMode.HALF_UP).doubleValue()).asObject());
            wholesalePrice.setCellValueFactory(cellData -> new SimpleDoubleProperty(BigDecimal.valueOf(cellData.getValue().getWholesalePrice()).setScale(2, RoundingMode.HALF_UP).doubleValue()).asObject());
        } else if (paymentBean.getBeanType().equals(PaymentBean.BeanType.DELIVERY)) {
            searchDeliveryPrice.setCellValueFactory(cellData -> new SimpleDoubleProperty(BigDecimal.valueOf(cellData.getValue().getDeliveryPrice()).setScale(2, RoundingMode.HALF_UP).doubleValue()).asObject());
        }

    }

    private void setupSellColumns(TableColumn<Product, String> nomenclature, TableColumn<Product, String> name, TableColumn<Product, Double> quantity, TableColumn<Product, String> quantityType, TableColumn<Product, Double> retailPrice, TableColumn<Product, Double> retailPriceDDS, TableColumn<Product, Double> totalRetailPrice, TableColumn<Product, Double> wholesalePrice, TableColumn<Product, Double> wholesalePriceDDS, TableColumn<Product, Double> totalWholesalePrice) {
        setupColumns(nomenclature, name, quantity, quantityType, retailPrice, wholesalePrice);

        retailPriceDDS.setCellValueFactory(cellData -> new SimpleDoubleProperty(BigDecimal.valueOf(cellData.getValue().getDdsRetailPrice()).setScale(2, RoundingMode.HALF_UP).doubleValue()).asObject());
        totalRetailPrice.setCellValueFactory(cellData -> new SimpleDoubleProperty(BigDecimal.valueOf(cellData.getValue().getRetailPrice()).setScale(2, RoundingMode.HALF_UP).doubleValue()).asObject());
        wholesalePriceDDS.setCellValueFactory(cellData -> new SimpleDoubleProperty(BigDecimal.valueOf(cellData.getValue().getDdsWholesalePrice()).setScale(2, RoundingMode.HALF_UP).doubleValue()).asObject());
        totalWholesalePrice.setCellValueFactory(cellData -> new SimpleDoubleProperty(BigDecimal.valueOf(cellData.getValue().getTotalWholesalePrice()).setScale(2, RoundingMode.HALF_UP).doubleValue()).asObject());
    }

    private void setupDeliveryColumns(TableColumn<Product, String> nomenclature, TableColumn<Product, String> name, TableColumn<Product, Double> quantity, TableColumn<Product, String> quantityType, TableColumn<Product, Double> deliveryPrice) {
        setupCommonColumns(nomenclature, name, quantity, quantityType);

        deliveryPrice.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getDeliveryPrice()).asObject());
    }

    public void createColumns() {
        searchTable.getColumns().clear();
        searchNomenclature = new TableColumn<>("Nomenclature");
        searchName = new TableColumn<>("Name");
        searchQuantity = new TableColumn<>("Quantity");
        searchQuantityType = new TableColumn<>("Quantity Type");

        searchTable.getColumns().addAll(searchNomenclature, searchName, searchQuantity, searchQuantityType);

        if (paymentBean.getBeanType().equals(PaymentBean.BeanType.SELL)) {
            searchRetailPrice = new TableColumn<>("Retail Price");
            searchWholesalePrice = new TableColumn<>("Wholesale Price");

            searchTable.getColumns().addAll(searchRetailPrice, searchWholesalePrice);
        } else if (paymentBean.getBeanType().equals(PaymentBean.BeanType.DELIVERY)) {
            searchDeliveryPrice = new TableColumn<>("Delivery Price");

            searchTable.getColumns().add(searchDeliveryPrice);
        }
    }

    private void createSellColumns() {
        productsTable.getColumns().clear();
        productsNomenclature = new TableColumn<>("Nomenclature");
        productsName = new TableColumn<>("Name");
        productsQuantity = new TableColumn<>("Quantity");
        productsQuantityType = new TableColumn<>("Quantity Type");
        productsRetailPrice = new TableColumn<>("Retail Price");
        productsWholesalePrice = new TableColumn<>("Wholesale Price");
        productsRetailDDS = new TableColumn<>("Retail DDS");
        productsTotalRetailPrice = new TableColumn<>("Total Retail Price");
        productsWholesaleDDS = new TableColumn<>("Wholesale DDS");
        productsTotalWholesalePrice = new TableColumn<>("Total Wholesale Price");
        productsTable.getColumns().addAll(productsNomenclature, productsName, productsQuantity, productsQuantityType, productsRetailPrice, productsRetailDDS, productsTotalRetailPrice, productsWholesalePrice, productsWholesaleDDS, productsTotalWholesalePrice);
    }

    private void createDeliveryColumns() {
        productsTable.getColumns().clear();
        productsNomenclature = new TableColumn<>("Nomenclature");
        productsName = new TableColumn<>("Name");
        productsQuantity = new TableColumn<>("Quantity");
        productsQuantityType = new TableColumn<>("Quantity Type");
        productsDeliveryPrice = new TableColumn<>("Delivery Price");
        productsTable.getColumns().addAll(productsNomenclature, productsName, productsQuantity, productsQuantityType, productsDeliveryPrice);
    }

    private ContextMenu getAddContextMenu() {
        final ContextMenu rowMenu = new ContextMenu();
        final MenuItem add = new MenuItem("Add");
        add.setOnAction(event -> addToProductsTable());
        rowMenu.getItems().add(add);
        return rowMenu;
    }

    private ContextMenu getEditDeleteContextMenu() {
        final ContextMenu rowMenu = new ContextMenu();
        final MenuItem edit = new MenuItem("Edit");
        final MenuItem delete = new MenuItem("Delete");
        edit.setOnAction(event -> editProduct());
        delete.setOnAction(event -> deleteProduct());
        rowMenu.getItems().addAll(edit, delete);
        return rowMenu;
    }

    private void addToProductsTable() {
        Product oldProduct = searchTable.getSelectionModel().getSelectedItem();
        Product product = productsData.stream().filter(p -> Objects.equals(p.getId(), oldProduct.getId())).findFirst().orElse(oldProduct.clone());
        boolean isNew = productsData.stream().noneMatch(p -> Objects.equals(p.getId(), product.getId()));
        Double oldQuantity = oldProduct.getQuantity();

        boolean isSell = paymentBean.getBeanType().equals(PaymentBean.BeanType.SELL);
        boolean isDelivery = paymentBean.getBeanType().equals(PaymentBean.BeanType.DELIVERY);

        Dialogs.singleTextInputDialog("0", "Enter quantity", "Quantity")
                .ifPresent(quantity -> {
                    if (!quantity.isEmpty() && Double.parseDouble(quantity) > 0 &&
                            ((isSell && Double.parseDouble(quantity) <= oldQuantity) || isDelivery)
                    ) {
                        if (isNew) {
                            product.setQuantity(Double.parseDouble(quantity));
                        } else {
                            product.setQuantity(product.getQuantity() + Double.parseDouble(quantity));
                        }

                        calcProductPrices(product);

                        if (isNew) {
                            productsData.add(product);
                            productsTable.setItems(productsData);
                        } else {
                            productsTable.refresh();
                        }

                        if (isSell) {
                            oldProduct.setQuantity(oldQuantity - Double.parseDouble(quantity));
                            searchTable.refresh();
                        }
                    } else {
                        if (isSell) {
                            Dialogs.warningDialog("Invalid quantity", "Quantity must be greater than 0 and less than or equal to " + oldQuantity);
                        } else if (isDelivery) {
                            Dialogs.warningDialog("Invalid quantity", "Quantity must be greater than 0");
                        }
                    }
                });
    }

    private void calcProductPrices(Product product) {
        product.setRetailPrice(product.getRetailPrice() * product.getQuantity());
        product.setDdsRetailPrice(product.getRetailPrice() * 0.2);
        product.setTotalRetailPrice(product.getRetailPrice() + product.getDdsRetailPrice());

        product.setWholesalePrice(product.getWholesalePrice() * product.getQuantity());
        product.setDdsWholesalePrice(product.getWholesalePrice() * 0.2);
        product.setTotalWholesalePrice(product.getWholesalePrice() + product.getDdsWholesalePrice());

        product.setDeliveryPrice(product.getDeliveryPrice() * product.getQuantity());
    }

    private void editProduct() {
        Product product = productsTable.getSelectionModel().getSelectedItem();
        Product oldProduct = searchData.stream().filter(p -> Objects.equals(p.getId(), product.getId())).findFirst().orElse(null);
        Double oldQuantity = product.getQuantity();

        Dialogs.singleTextInputDialog(product.getQuantity().toString(), "Enter quantity", "Quantity")
                .ifPresent(quantity -> {
                    if (!quantity.isEmpty() && Double.parseDouble(quantity) > 0 && Double.parseDouble(quantity) <= Objects.requireNonNull(oldProduct).getQuantity()) {
                        product.setQuantity(Double.parseDouble(quantity));

                        calcProductPrices(product);

                        productsTable.refresh();

                        oldProduct.setQuantity(oldProduct.getQuantity() + (oldQuantity - Double.parseDouble(quantity)));
                        searchTable.refresh();
                    } else {
                        Dialogs.warningDialog("Invalid quantity", "Quantity must be greater than 0 and less than or equal to " + Objects.requireNonNull(oldProduct).getQuantity());
                    }
                });
    }

    private void deleteProduct() {
        Product product = productsTable.getSelectionModel().getSelectedItem();
        Product oldProduct = searchData.stream().filter(p -> Objects.equals(p.getId(), product.getId())).findFirst().orElse(null);

        Objects.requireNonNull(oldProduct).setQuantity(oldProduct.getQuantity() + product.getQuantity());
        searchTable.refresh();

        productsData.remove(product);
        productsTable.setItems(productsData);
    }

    private void setupCategories() {
        List<ProductCategory> productCategoriesList = productCategoryService.getAllProductCategories();

        productCategories.getItems().addAll(productCategoriesList);

        productCategories.converterProperty().setValue(new StringConverter<>() {
            @Override
            public String toString(ProductCategory object) {
                return object.getName();
            }

            @Override
            public ProductCategory fromString(String string) {
                return productCategoriesList.stream().filter(productCategory -> productCategory.getName().equals(string)).findFirst().orElse(null);
            }
        });

        productCategories.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> setupProducts(oldValue, newValue));

        productCategories.getSelectionModel().selectFirst();
    }

    private void setupProducts(ProductCategory oldCategory, ProductCategory productCategory) {
        if (paymentBean.getSearchData() != null && oldCategory.equals(productCategory)) {
            searchData = paymentBean.getSearchData();
        } else {
            searchData = FXCollections.observableArrayList(productService.getProductsByProductCategoryType(productCategory.getSlug()));
            paymentBean.setSearchData(searchData);
        }

        searchFilteredList = new FilteredList<>(searchData, p -> true);

        searchTable.setItems(searchFilteredList);

        if (paymentBean.getProductsData() != null) {
            productsData = paymentBean.getProductsData();
        } else {
            productsData = FXCollections.observableArrayList();
            paymentBean.setProductsData(productsData);
        }

        productsTable.setItems(productsData);

        productsTable.refresh();
        searchTable.refresh();
    }
}
