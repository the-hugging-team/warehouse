package com.the.hugging.team.utils;

import com.the.hugging.team.controllers.dialogs.SaleInvoiceDialog;
import com.the.hugging.team.entities.*;
import com.the.hugging.team.services.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Dialogs {
    private static final SaleService saleService = SaleService.getInstance();
    private static final ProductService productService = ProductService.getInstance();
    private static final ProductQuantityTypeService productQuantityTypeService = ProductQuantityTypeService.getInstance();
    private static final StorageService storageService = StorageService.getInstance();
    private static final ProductCategoryService productCategoryService = ProductCategoryService.getInstance();
    private static final ActivityService activityService = ActivityService.getInstance();

    public static void notSelectedWarning() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Window.CELLABLUE_PATH));
        alert.setGraphic(null);
        alert.setTitle("Warning: Item not selected");
        alert.setHeaderText(null);
        alert.setContentText("Please select an item from the list!");
        alert.showAndWait();
    }

    public static void warningDialog(String title, String content) {
        showWarningDialog(title, content, true);
    }

    public static void warningDialog(String title, String content, boolean showAndWait) {
        showWarningDialog(title, content, showAndWait);
    }

    private static void showWarningDialog(String title, String content, boolean showAndWait) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Window.CELLABLUE_PATH));
        alert.setGraphic(null);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        if (showAndWait) {
            alert.showAndWait();
        } else {
            alert.show();
        }
    }

    public static Optional<String> singleTextInputDialog(String message, String title, String content) {
        TextInputDialog dialog = new TextInputDialog(message);
        ((Stage) dialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Window.CELLABLUE_PATH));
        dialog.setGraphic(null);
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        dialog.setContentText(content);

        return dialog.showAndWait();
    }

    public static Optional<User> userDialog(User user, String title) {
        Dialog<User> dialog = new Dialog<>();
        ((Stage) dialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Window.CELLABLUE_PATH));
        dialog.setGraphic(null);
        dialog.setTitle(title);
        dialog.setHeaderText(null);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField firstName = new TextField();
        firstName.setPromptText("First name");

        if (user.getFirstName() != null) {
            firstName.setText(user.getFirstName());
        }

        TextField lastName = new TextField();
        lastName.setPromptText("Last name");

        if (user.getLastName() != null) {
            lastName.setText(user.getLastName());
        }

        ChoiceBox<String> sex = new ChoiceBox<>(FXCollections.observableArrayList("Male", "Female"));
        sex.getSelectionModel().selectFirst();

        if (user.getSex() != null) {
            sex.getSelectionModel().select(user.getSex() - 1);
        }

        TextField username = new TextField();
        username.setPromptText("Username");

        if (user.getUsername() != null) {
            username.setText(user.getUsername());
        }

        TextField password = new TextField();
        password.setPromptText("Password");
        Button randomPassword = new Button("Generate random password");
        randomPassword.setOnAction(event -> password.setText(Hasher.generateRandomPassword(8, 12)));

        if (user.getUsername() != null) {
            username.setText(user.getUsername());
        }

        grid.add(new Label("First name:"), 0, 0);
        grid.add(firstName, 1, 0);

        grid.add(new Label("Last name:"), 0, 1);
        grid.add(lastName, 1, 1);

        grid.add(new Label("Sex:"), 0, 2);
        grid.add(sex, 1, 2);

        grid.add(new Label("Username:"), 0, 4);
        grid.add(username, 1, 4);

        if (user.getPassword() == null) {
            grid.add(new Label("Password:"), 0, 5);
            grid.add(password, 1, 5);
            grid.add(randomPassword, 2, 5);
        }

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                user.setFirstName(firstName.getText());
                user.setLastName(lastName.getText());
                user.setSex(sex.getSelectionModel().getSelectedIndex() + 1);
                user.setUsername(username.getText());
                user.setPassword(Hasher.hash(password.getText()));
                return user;
            }
            return null;
        });

        return dialog.showAndWait();
    }

    public static Optional<Company> companyDialog(Company company, String title) {
        Dialog<Company> dialog = new Dialog<>();
        ((Stage) dialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Window.CELLABLUE_PATH));
        dialog.setGraphic(null);
        dialog.setTitle(title);
        dialog.setHeaderText(null);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField companyName = new TextField();
        companyName.setPromptText("Name");

        TextField companyAddress = new TextField();
        companyAddress.setPromptText("Address");

        TextField bulstat = new TextField();
        bulstat.setPromptText("EIK");

        TextField dds = new TextField();
        dds.setPromptText("DDS number");

        TextField mol = new TextField();
        mol.setPromptText("MOL");

        if (company.getName() != null) {
            companyName.setText(company.getName());
        }

        if (company.getAddress() != null) {
            companyAddress.setText(company.getAddress().getAddress());
        }

        if (company.getBulstat() != null) {
            bulstat.setText(company.getBulstat());
        }

        if (company.getDdsNumber() != null) {
            dds.setText(company.getDdsNumber());
        }

        if (company.getMol() != null) {
            mol.setText(company.getMol());
        }

        grid.add(new Label("Company Name:"), 0, 0);
        grid.add(companyName, 1, 0);

        grid.add(new Label("Company Address:"), 0, 1);
        grid.add(companyAddress, 1, 1);

        grid.add(new Label("EIK(Bulstat):"), 0, 2);
        grid.add(bulstat, 1, 2);

        grid.add(new Label("DDS number:"), 0, 3);
        grid.add(dds, 1, 3);

        grid.add(new Label("MOL:"), 0, 4);
        grid.add(mol, 1, 4);

        dialog.getDialogPane().setContent(grid);

        final Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.addEventFilter(
                ActionEvent.ACTION,
                event -> {
                    if (bulstat.getText().length() != 9 &&
                            bulstat.getText().length() != 10 &&
                            bulstat.getText().length() != 13) {
                        Dialogs.warningDialog("Incorrect data", "Incorrect EIK format!");
                        event.consume();
                    } else if (dds.getText().length() != 11 &&
                            dds.getText().length() != 12) {
                        Dialogs.warningDialog("Incorrect data", "Incorrect DDS number format!");
                        event.consume();
                    }
                }
        );

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                Address newAddress = new Address();
                newAddress.setAddress(companyAddress.getText());
                company.setName(companyName.getText());
                company.setAddress(newAddress);
                company.setBulstat(bulstat.getText());
                company.setDdsNumber(dds.getText());
                company.setMol(mol.getText());
                return company;
            }
            return null;
        });

        return dialog.showAndWait();
    }

    public static Optional<Product> productDialog(Product product, String title) {
        Dialog<Product> dialog = new Dialog<>();
        ((Stage) dialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Window.CELLABLUE_PATH));
        dialog.setGraphic(null);
        dialog.setTitle(title);
        dialog.setHeaderText(null);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField productName = new TextField();
        productName.setPromptText("Name");
        productName.setPrefWidth(250);

        TextField nomenclature = new TextField();
        nomenclature.setPromptText("Nomenclature");
        nomenclature.setPrefWidth(250);

        TextField quantityAmount = new TextField();
        quantityAmount.setPromptText("Quantity amount");
        quantityAmount.setPrefWidth(250);

        TextField retailPrice = new TextField();
        retailPrice.setPromptText("Retail price");
        retailPrice.setPrefWidth(250);

        TextField wholesalePrice = new TextField();
        wholesalePrice.setPromptText("Wholesale price");
        wholesalePrice.setPrefWidth(250);

        TextField deliveryPrice = new TextField();
        deliveryPrice.setPromptText("Delivery price");
        deliveryPrice.setPrefWidth(250);

        ChoiceBox<ProductCategory> category = new ChoiceBox<>(FXCollections.observableArrayList(productCategoryService.getAllProductCategories()));
        StringConverter<ProductCategory> categoryConverter = new StringConverter<>() {
            @Override
            public String toString(ProductCategory productCategory) {
                return productCategory.getName();
            }

            @Override
            public ProductCategory fromString(String s) {
                return null;
            }
        };
        category.setConverter(categoryConverter);
        category.getSelectionModel().selectFirst();

        ChoiceBox<ProductQuantityType> quantityType = new ChoiceBox<>(FXCollections.observableArrayList(productQuantityTypeService.getAllProductQuantityTypes()));
        StringConverter<ProductQuantityType> productQuantityTypeConverter = new StringConverter<>() {
            @Override
            public String toString(ProductQuantityType productQuantityType) {
                return productQuantityType.getName();
            }

            @Override
            public ProductQuantityType fromString(String s) {
                return null;
            }
        };
        quantityType.setConverter(productQuantityTypeConverter);
        quantityType.getSelectionModel().selectFirst();

        ChoiceBox<Shelf> shelf = new ChoiceBox<>(FXCollections.observableArrayList(storageService.getAllShelves()));
        StringConverter<Shelf> shelfStringConverter = new StringConverter<>() {
            @Override
            public String toString(Shelf shelf) {
                return shelf.getName();
            }

            @Override
            public Shelf fromString(String s) {
                return null;
            }
        };
        shelf.setConverter(shelfStringConverter);
        shelf.getSelectionModel().selectFirst();

        if (product.getId() != null) {
            productName.setText(product.getName());
            nomenclature.setText(product.getNomenclature());
            category.getSelectionModel().select(product.getProductCategory());
            quantityAmount.setText(product.getQuantity().toString());
            retailPrice.setText(product.getRetailPrice().toString());
            wholesalePrice.setText(product.getWholesalePrice().toString());
            deliveryPrice.setText(product.getDeliveryPrice().toString());
            quantityType.getSelectionModel().select(product.getProductQuantityType());
            shelf.getSelectionModel().select(product.getShelf());
        }

        TextField newQuantityTypeName = new TextField();
        newQuantityTypeName.setPromptText("New quantity type name");
        newQuantityTypeName.setText(null);

        grid.add(new Label("Product Name:"), 0, 0);
        grid.add(productName, 1, 0);

        grid.add(new Label("Nomenclature:"), 0, 1);
        grid.add(nomenclature, 1, 1);

        grid.add(new Label("Category:"), 0, 2);
        grid.add(category, 1, 2);

        grid.add(new Label("Quantity amount:"), 0, 3);
        grid.add(quantityAmount, 1, 3);

        grid.add(new Label("Quantity type:"), 0, 4);
        grid.add(quantityType, 1, 4);

        grid.add(new Label("Retail price:"), 0, 5);
        grid.add(retailPrice, 1, 5);

        grid.add(new Label("Wholesale price:"), 0, 6);
        grid.add(wholesalePrice, 1, 6);

        grid.add(new Label("Delivery price:"), 0, 7);
        grid.add(deliveryPrice, 1, 7);

        grid.add(new Label("Shelf:"), 0, 8);
        grid.add(shelf, 1, 8);

        dialog.getDialogPane().setContent(grid);

        final Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);

        okButton.addEventFilter(ActionEvent.ACTION, event -> {
            try {
                Double.parseDouble(quantityAmount.getText());
                Double.parseDouble(retailPrice.getText());
                Double.parseDouble(wholesalePrice.getText());
                Double.parseDouble(deliveryPrice.getText());
            } catch (Exception e) {
                Dialogs.warningDialog("Incorrect data", "Incorrect data in the amount or price fields.");
                event.consume();
            }
        });

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                //make product entity
                double newAmount, newRetailPrice, newWholesalePrice, newDeliveryPrice;
                newAmount = Double.parseDouble(quantityAmount.getText());
                newRetailPrice = Double.parseDouble(retailPrice.getText());
                newWholesalePrice = Double.parseDouble(wholesalePrice.getText());
                newDeliveryPrice = Double.parseDouble(deliveryPrice.getText());

                product.setName(productName.getText());
                product.setNomenclature(nomenclature.getText());

                product.setProductCategory(category.getSelectionModel().getSelectedItem());

                product.setQuantity(newAmount);

                product.setProductQuantityType(quantityType.getSelectionModel().getSelectedItem());

                product.setRetailPrice(newRetailPrice);
                product.setWholesalePrice(newWholesalePrice);
                product.setDeliveryPrice(newDeliveryPrice);
                product.setShelf(shelf.getSelectionModel().getSelectedItem());

                return product;
            }
            return null;
        });

        return dialog.showAndWait();
    }

    public static void cashRegisterSaleHistoryDialog(CashRegister cr) {
        Dialog<CashRegister> dialog = new Dialog<>();
        ((Stage) dialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Window.CELLABLUE_PATH));
        dialog.setGraphic(null);
        dialog.setTitle("Cash register " + cr.getId() + " sale history");
        dialog.setResizable(false);

        ObservableList<Sale> data = FXCollections.observableArrayList(saleService.getSalesByCashRegister(cr));

        if (data.size() == 0) {
            dialog.setHeaderText("Nothing to show");
            dialog.getDialogPane().setPrefWidth(500);
        } else {
            dialog.setHeaderText(null);
            dialog.getDialogPane().setPrefSize(500, 600);

            TableView<Sale> table = new TableView<>();
            TableColumn<Sale, String> transaction = new TableColumn<>();
            TableColumn<Sale, String> operator = new TableColumn<>();
            TableColumn<Sale, String> time = new TableColumn<>();

            transaction.setCellValueFactory(cellData ->
                    new SimpleStringProperty((cellData.getValue().getTransaction().getTransactionType().getSlug().equals("transaction_types.sell") ? "+" : "-")
                            + cellData.getValue().getTransaction().getAmount().toString()));
            transaction.setText("Transaction");

            operator.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCreatedBy().getFirstName() + " " +
                    cellData.getValue().getCreatedBy().getLastName()));
            operator.setText("Operator");

            time.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCreatedAt().toString()));
            time.setText("Time");

            table.getColumns().addAll(transaction, time, operator);
            table.setEditable(false);
            table.getItems().setAll(data);
            int columns = table.getColumns().size();
            for (int i = 0; i < columns; i++) {
                table.getColumns().get(i).setPrefWidth((dialog.getDialogPane().getPrefWidth() - 10 * 2) / columns);
                table.getColumns().get(i).setReorderable(false);
                table.getColumns().get(i).setResizable(false);
            }

            table.setRowFactory(tv -> {
                final TableRow<Sale> row = new TableRow<>();

                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        try {
                            invoiceBySaleDialog(table.getSelectionModel().getSelectedItem());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                return row;
            });

            dialog.getDialogPane().setContent(table);
        }
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        dialog.show();
    }

    private static void invoiceBySaleDialog(Sale sale) throws IOException {
        Dialog<Invoice> dialog = new Dialog<>();

        if (sale.getInvoice() == null) {
            ((Stage) dialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Window.CELLABLUE_PATH));
            dialog.setGraphic(null);
            dialog.setTitle("Invoice " + sale.getInvoice().getId());
            dialog.setResizable(false);
            dialog.setHeaderText("There is no invoice for this sale");
            dialog.getDialogPane().setPrefWidth(500);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
            dialog.show();
        } else {
            SaleInvoiceDialog saleInvoiceDialog = new SaleInvoiceDialog(sale, dialog.getOwner());
            saleInvoiceDialog.show();
        }
    }

    public static void userActivitiesDialog(User user) {
        Dialog<Activity> dialog = new Dialog<>();
        ((Stage) dialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Window.CELLABLUE_PATH));
        dialog.setGraphic(null);
        dialog.setTitle("Activities of " + user.getFirstName() + ' ' + user.getLastName());
        dialog.setResizable(false);

        Comparator<Activity> activityComparator = Comparator.comparing(Activity::getCreatedAt);

        List<Activity> sortedActivities = activityService.getActivitiesByUser(user);
        sortedActivities.sort(activityComparator.reversed());

        ObservableList<Activity> data = FXCollections.observableArrayList(sortedActivities);

        if (data.size() == 0) {
            dialog.setHeaderText("Nothing to show");
            dialog.getDialogPane().setPrefWidth(500);
        } else {
            dialog.setHeaderText(null);
            dialog.getDialogPane().setPrefSize(500, 600);

            TableView<Activity> table = new TableView<>();
            TableColumn<Activity, String> name = new TableColumn<>();
            TableColumn<Activity, String> time = new TableColumn<>();

            name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getActivityType().getName()));
            name.setText("Activity");

            time.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCreatedAt().toString()));
            time.setText("Time");

            table.getColumns().addAll(name, time);
            table.setEditable(false);
            table.getItems().setAll(data);
            int columns = table.getColumns().size();
            for (int i = 0; i < columns; i++) {
                table.getColumns().get(i).setPrefWidth((dialog.getDialogPane().getPrefWidth() - 10 * 2) / columns);
                table.getColumns().get(i).setReorderable(false);
                table.getColumns().get(i).setResizable(false);
            }

            dialog.getDialogPane().setContent(table);
        }
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        dialog.show();
    }

    public static void shelfItemsDialog(Shelf shelf) {
        Dialog<Shelf> dialog = new Dialog<>();
        ((Stage) dialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Window.CELLABLUE_PATH));
        dialog.setGraphic(null);
        dialog.setTitle("Shelf " + shelf.getName() + " items");
        dialog.setResizable(false);

        ObservableList<Product> data = FXCollections.observableArrayList(productService.getProductsByShelf(shelf));

        if (data.size() == 0) {
            dialog.setHeaderText("Nothing to show");
            dialog.getDialogPane().setPrefWidth(500);
        } else {
            dialog.setHeaderText(null);
            dialog.getDialogPane().setPrefSize(1300, 600);

            FilteredList<Product> filteredList = new FilteredList<>(data, p -> true);

            TableView<Product> table = new TableView<>();
            TableColumn<Product, String> name = new TableColumn<>();
            TableColumn<Product, String> nomenclature = new TableColumn<>();
            TableColumn<Product, String> category = new TableColumn<>();
            TableColumn<Product, String> quantity = new TableColumn<>();
            TableColumn<Product, String> retailPrice = new TableColumn<>();
            TableColumn<Product, String> wholesalePrice = new TableColumn<>();
            TableColumn<Product, String> deliveryPrice = new TableColumn<>();

            name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
            name.setText("Name");

            nomenclature.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomenclature()));
            nomenclature.setText("Nomenclature");

            category.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductCategory().getName()));
            category.setText("Category");

            quantity.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getQuantity().toString() + " " + cellData.getValue().getProductQuantityType().getName()));
            quantity.setText("Quantity");

            retailPrice.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRetailPrice().toString()));
            retailPrice.setText("Retail price");

            wholesalePrice.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getWholesalePrice().toString()));
            wholesalePrice.setText("Wholesale price");

            deliveryPrice.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDeliveryPrice().toString()));
            deliveryPrice.setText("Delivery price");

            table.getColumns().addAll(name, nomenclature, category, quantity, retailPrice, wholesalePrice, deliveryPrice);

            table.setEditable(false);
            table.getItems().setAll(filteredList);
            int columns = table.getColumns().size();
            for (int i = 0; i < columns; i++) {
                table.getColumns().get(i).setPrefWidth((dialog.getDialogPane().getPrefWidth() - 10 * 2) / columns);
                table.getColumns().get(i).setReorderable(false);
                table.getColumns().get(i).setResizable(false);
            }

            dialog.getDialogPane().setContent(table);
        }
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        dialog.show();
    }

    public static void notificationsDialog(ObservableList<Notification> notifications)
    {
        Dialog<User> dialog = new Dialog<>();
        ((Stage) dialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Window.CELLABLUE_PATH));
        dialog.setGraphic(null);
        dialog.setTitle("Notifications");
        dialog.setResizable(false);

        if (notifications.size() == 0)
        {
            dialog.setHeaderText("No notifications to show.");
            dialog.getDialogPane().setPrefWidth(500);
        }
        else {
            dialog.setHeaderText(null);
            dialog.getDialogPane().setPrefSize(700, 600);

            TableView<Notification> notificationsTable = new TableView<>();
            TableColumn<Notification, String> notification = new TableColumn<>();
            TableColumn<Notification, String> time = new TableColumn<>();

            notification.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNotification()));
            notification.setText("Notification");
            notification.setPrefWidth((dialog.getDialogPane().getPrefWidth() - 10 * 2) - 150);
            notification.setResizable(false);

            time.setCellValueFactory(cellData ->
                    {
                        Timestamp now = new Timestamp(System.currentTimeMillis());
                        return new SimpleStringProperty(timePassedByMillis(now.getTime() - cellData.getValue().getCreatedAt().getTime()));
                    });
            time.setText("Time");
            time.setPrefWidth(150);
            notification.setResizable(false);

            notificationsTable.getColumns().addAll(notification, time);
            notificationsTable.setEditable(false);
            notificationsTable.getItems().setAll(notifications);

            notificationsTable.setRowFactory(new Callback<>() {
                @Override
                public TableRow<Notification> call(TableView<Notification> param) {
                    return new TableRow<>() {
                        @Override
                        protected void updateItem(Notification row, boolean empty) {
                            super.updateItem(row, empty);

                            if (!empty && row.getReadAt() == null)
                                styleProperty().setValue(styleProperty().getValue().concat("-fx-font-weight: bold;"));
                        }
                    };
                }
            });

            dialog.getDialogPane().setContent(notificationsTable);
        }
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        dialog.show();
    }

    private static String timePassedByMillis(long millis)
    {
        if (millis < 60000)
        {
            return "less than a minute ago";
        }
        else if (millis < 3600000)
        {
            return millis / 60000 + " minutes ago";
        }
        else if (millis < 86400000)
        {
            return millis / 3600000 + " hours ago";
        }
        else return millis / 86400000 + " days ago";
    }
}
