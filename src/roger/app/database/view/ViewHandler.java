package roger.app.database.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import roger.app.database.model.medicine.Medicine;

import java.io.IOException;
import java.util.Optional;

public class ViewHandler {

    private static Stage primaryStage;

    public ViewHandler(Stage stage) {
        primaryStage = stage;
    }

    public static void loadLoginPage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewHandler.class.getResource("loginPage.fxml"));
            AnchorPane pane = loader.load();

            LoginPage loginPageController = loader.getController();

            primaryStage.setTitle("Login Page");
            primaryStage.setScene(new Scene(pane));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadMenuPage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewHandler.class.getResource("MenuPage.fxml"));
            AnchorPane pane = loader.load();
            //    menuStage.initModality(Modality.WINDOW_MODAL);
            // menuStage.initOwner(primaryStage);

            MenuPage menuPage = loader.getController();

            primaryStage.setTitle("Menu Page");
            primaryStage.setScene(new Scene(pane));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadSellPage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewHandler.class.getResource("SellPage.fxml"));
            AnchorPane pane = loader.load();

            SellPage sellPage = loader.getController();

            primaryStage.setTitle("Sell Page");
            primaryStage.setScene(new Scene(pane));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadViewPage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewHandler.class.getResource("ViewPage.fxml"));
            AnchorPane pane = loader.load();

            ViewPage viewPage = loader.getController();

            primaryStage.setTitle("View Page");
            primaryStage.setScene(new Scene(pane));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int amountPrompt(Medicine medicine) {
        TextInputDialog dialog = new TextInputDialog("Amount");
        dialog.setTitle("Enter Amount");
        dialog.setHeaderText("There are " + medicine.getQuantity() + " " + medicine.getMedicineName() + " left");
        dialog.setContentText("Please the amount:");
        int amount = 0;
        Optional<String> amountInput = dialog.showAndWait();
        if (amountInput.isPresent())
            amount = Integer.parseInt(amountInput.get());
        return amount;
    }

    public static boolean showMedicineEditDialog(Medicine medicine) {
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
}
