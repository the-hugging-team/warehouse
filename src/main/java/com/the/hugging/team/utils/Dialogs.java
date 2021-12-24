package com.the.hugging.team.utils;

import com.the.hugging.team.entities.*;
import com.the.hugging.team.services.ProductService;
import com.the.hugging.team.services.SaleService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Optional;

public class Dialogs {
    private static final SaleService saleService = SaleService.getInstance();
    private static final ProductService productService = ProductService.getInstance();

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
        Alert alert = new Alert(Alert.AlertType.WARNING);
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Window.CELLABLUE_PATH));
        alert.setGraphic(null);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
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

        ChoiceBox sex = new ChoiceBox(FXCollections.observableArrayList("Male", "Female"));
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

            FilteredList<Sale> filteredList = new FilteredList<>(data, p -> true);

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
            table.getItems().setAll(filteredList);
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
                        invoiceBySaleDialog(table.getSelectionModel().getSelectedItem().getInvoice());
                    }
                });

                return row;
            });

            dialog.getDialogPane().setContent(table);
        }
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        dialog.show();
    }

    private static void invoiceBySaleDialog(Invoice invoice) {
        Dialog<Invoice> dialog = new Dialog<>();
        ((Stage) dialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Window.CELLABLUE_PATH));
        dialog.setGraphic(null);
        dialog.setTitle("Invoice " + invoice.getId());
        dialog.setResizable(false);

        if (invoice == null) {
            dialog.setHeaderText("There is no invoice for this sale");
            dialog.getDialogPane().setPrefWidth(500);
        } else {
            AnchorPane windowAnchor = new AnchorPane();

            //set up the anchor

            dialog.getDialogPane().setContent(windowAnchor);
        }
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        dialog.show();
    }

    public static void userActivitiesDialog(User user)
    {

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
}
