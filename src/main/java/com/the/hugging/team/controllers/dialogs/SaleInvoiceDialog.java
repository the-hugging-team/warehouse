package com.the.hugging.team.controllers.dialogs;

import com.the.hugging.team.entities.Sale;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class SaleInvoiceDialog extends Dialog<Sale> {
    public SaleInvoiceDialog(Sale sale, Window owner) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/dialogs/invoice-dialog.fxml"));
        loader.setController(this);

        DialogPane dialogPane = new DialogPane();
        try {
            dialogPane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        initOwner(owner);
        initModality(Modality.APPLICATION_MODAL);

        ((Stage)dialogPane.getScene().getWindow()).getIcons().add(new Image(com.the.hugging.team.utils.Window.CELLABLUE_PATH));

        setResizable(false);
        setTitle("Invoice " + sale.getInvoice().getId());
        setDialogPane(dialogPane);
        setGraphic(null);
    }
}
