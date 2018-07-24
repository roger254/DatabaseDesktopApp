package roger.app.database.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import roger.app.database.model.medicine.Medicine;
import roger.app.database.model.medicine.MedicineHandler;
import roger.app.database.model.users.UserHandler;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class ViewHandler {

    private static Stage primaryStage;

    ViewHandler(Stage stage) {
        primaryStage = stage;
    }

    static void loadLoginPage() {
        UserHandler.loadUsers();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewHandler.class.getResource("loginPage.fxml"));
            AnchorPane pane = loader.load();
            loader.getController();
            primaryStage.setTitle("Login Page");
            primaryStage.setScene(new Scene(pane));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MedicineHandler.loadMedicineData();
    }

    static void loadMenuPage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewHandler.class.getResource("MenuPage.fxml"));
            AnchorPane pane = loader.load();
            loader.getController();
            primaryStage.setTitle("Menu Page");
            primaryStage.setScene(new Scene(pane));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void loadSellPage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewHandler.class.getResource("SellPage.fxml"));
            AnchorPane pane = loader.load();
            loader.getController();
            primaryStage.setTitle("Sell Page");
            primaryStage.setScene(new Scene(pane));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void loadViewPage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewHandler.class.getResource("ViewPage.fxml"));
            AnchorPane pane = loader.load();
            loader.getController();
            primaryStage.setTitle("View Page");
            primaryStage.setScene(new Scene(pane));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static int amountPrompt(Medicine medicine) {
        TextInputDialog dialog = new TextInputDialog("Amount");
        dialog.setTitle("Enter Amount");
        dialog.setHeaderText("There are " + medicine.getQuantity() + " " + medicine.getMedicineName() + " left");
        dialog.setContentText("Please the amount:");
        int amount = 0;
        Optional<String> amountInput = dialog.showAndWait();
        if (amountInput.isPresent()) {
            amount = Integer.parseInt(amountInput.get());
            if (amount > medicine.getQuantity() || amount < 1) {
                alertPrompt(medicine);
                return 0;
            }
        }
        return amount;
    }

    static boolean showMedicineEditDialog(Medicine medicine) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewHandler.class.getResource("EditDialog.fxml"));
            AnchorPane pane = loader.load();

            //create dialog Stage
            Stage editStage = new Stage();
            editStage.setTitle("Edit Item");
            editStage.initModality(Modality.WINDOW_MODAL);
            //      editStage.initOwner(main.getPrimaryStage());
            Scene scene = new Scene(pane);
            editStage.setScene(scene);

            //set The medicine dialog controller
            EditDialog editDialogController = loader.getController();
            editDialogController.setDialogStage(editStage);
            editDialogController.setMedicine(medicine);

            editStage.showAndWait();

            return editDialogController.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void alertPrompt(Medicine medicine) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(primaryStage);
        alert.setTitle("Invalid Exceeded");
        if (medicine.getQuantity() == 1)
            alert.setHeaderText("There is only " + medicine.getQuantity() + " " + medicine.getMedicineName() + " left");
        else
            alert.setHeaderText("There are only " + medicine.getQuantity() + " " + medicine.getMedicineName() + "s left");
        alert.setContentText("Please enter a valid amount");
        alert.showAndWait();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void showAlert(File file, String process) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Could not " + process + " data");
        alert.setContentText("Could not " + process + " data to file:\n" + file.getPath());

        alert.showAndWait();
    }
}
