package com.the.hugging.team.controllers.wizards;

import com.the.hugging.team.entities.Product;
import com.the.hugging.team.entities.ProductCategory;
import com.the.hugging.team.services.ProductCategoryService;
import com.the.hugging.team.services.ProductService;
import com.the.hugging.team.utils.Dialogs;
import com.the.hugging.team.utils.TableResizer;
import com.the.hugging.team.utils.WindowHandler;
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

import java.util.List;

public class SelectProductsController extends WindowHandler {

    private final ProductCategoryService productCategoryService = ProductCategoryService.getInstance();
    private final ProductService productService = ProductService.getInstance();

    @FXML
    private AnchorPane wizardStepPane;

    @FXML
    private TextField searchField;
    @FXML
    private ChoiceBox<ProductCategory> productCategories;

    @FXML
    private TableView<Product> searchTable;
    @FXML
    private TableColumn<Product, String> searchNomenclature;
    @FXML
    private TableColumn<Product, String> searchName;
    @FXML
    private TableColumn<Product, Double> searchQuantity;
    @FXML
    private TableColumn<Product, String> searchQuantityType;
    @FXML
    private TableColumn<Product, Double> searchRetailPrice;
    @FXML
    private TableColumn<Product, Double> searchWholesalePrice;

    private ObservableList<Product> searchData;
    private FilteredList<Product> searchFilteredList;

    @FXML
    private TableView<Product> productsTable;
    @FXML
    private TableColumn<Product, String> productsNomenclature;
    @FXML
    private TableColumn<Product, String> productsName;
    @FXML
    private TableColumn<Product, Double> productsQuantity;
    @FXML
    private TableColumn<Product, String> productsQuantityType;
    @FXML
    private TableColumn<Product, Double> productsRetailPrice;
    @FXML
    private TableColumn<Product, Double> productsRetailDDS;
    @FXML
    private TableColumn<Product, Double> productsTotalRetailPrice;
    @FXML
    private TableColumn<Product, Double> productsWholesalePrice;
    @FXML
    private TableColumn<Product, Double> productsWholesaleDDS;
    @FXML
    private TableColumn<Product, Double> productsTotalWholesalePrice;

    @FXML
    private void initialize() {
        setupTables();
        setupCategories();
    }

    private void setupTables() {
        // Search table

        searchNomenclature.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomenclature()));
        searchName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        searchQuantity.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getQuantity()).asObject());
        searchQuantityType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductQuantityType().getName()));
        searchRetailPrice.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getRetailPrice()).asObject());
        searchWholesalePrice.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getWholesalePrice()).asObject());

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
        productsNomenclature.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomenclature()));
        productsName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        productsQuantity.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getQuantity()).asObject());
        productsQuantityType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductQuantityType().getName()));
        productsRetailPrice.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getRetailPrice()).asObject());
        productsRetailDDS.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getDdsRetailPrice()).asObject());
        productsTotalRetailPrice.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getTotalRetailPrice()).asObject());
        productsWholesalePrice.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getWholesalePrice()).asObject());
        productsWholesaleDDS.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getDdsWholesalePrice()).asObject());
        productsTotalWholesalePrice.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getTotalWholesalePrice()).asObject());

        TableResizer.setCustomColumns(productsTable, List.of(0, 1, 2, 3, 9), List.of(100, 150, 75, 55, 150));
    }

    private ContextMenu getAddContextMenu() {
        final ContextMenu rowMenu = new ContextMenu();
        final MenuItem add = new MenuItem("Add");
        add.setOnAction(event -> addToProductsTable());
        rowMenu.getItems().add(add);
        return rowMenu;
    }

    private void addToProductsTable() {
        Product product = searchTable.getSelectionModel().getSelectedItem().clone();

        Dialogs.singleTextInputDialog("0", "Enter quantity", "Quantity")
                .ifPresent(quantity -> {
                    product.setQuantity(Double.parseDouble(quantity));

                    product.setRetailPrice(product.getRetailPrice() * product.getQuantity());
                    product.setDdsRetailPrice(product.getRetailPrice() * 0.2);
                    product.setTotalRetailPrice(product.getRetailPrice() + product.getDdsRetailPrice());

                    product.setWholesalePrice(product.getWholesalePrice() * product.getQuantity());
                    product.setDdsWholesalePrice(product.getWholesalePrice() * 0.2);
                    product.setTotalWholesalePrice(product.getWholesalePrice() + product.getDdsWholesalePrice());

                    productsTable.getItems().add(product);
                });
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

        productCategories.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> setupProducts(newValue));

        productCategories.getSelectionModel().selectFirst();
    }

    private void setupProducts(ProductCategory productCategory) {
        searchData = FXCollections.observableArrayList(productService.getProductsByProductCategoryType(productCategory.getSlug()));

        searchFilteredList = new FilteredList<>(searchData, p -> true);

        searchTable.setItems(searchFilteredList);
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
}
