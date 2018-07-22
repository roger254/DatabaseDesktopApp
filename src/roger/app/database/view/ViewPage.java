package roger.app.database.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import roger.app.database.model.medicine.Medicine;
import roger.app.database.model.medicine.MedicineHandler;
import roger.app.database.model.utils.DateUtil;

import java.io.IOException;

public class ViewPage {

    private Stage primaryStage;

    @FXML
    private TableView<Medicine> medicineTable;
    @FXML
    private TableColumn<Medicine, String> medicineNameColumn;
    @FXML
    private TableColumn<Medicine, Integer> medicineQuantityColumn;

    @FXML
    private Label nameLabel;

    @FXML
    private Button editButton;

    @FXML
    private TextArea descriptionText;

    @FXML
    private Label quantityLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label entryDateLabel;

    @FXML
    private ComboBox<String> checkOutBox;

    private Main main;


    private MenuPage menuPage;

    private String userAccess;

    private boolean okClicked = false;

    private LoginPage loginPage;


    //called after fxml file has been loaded
    @FXML
    private void initialize() {
        //initialize data table
        medicineTable.setItems(MedicineHandler.getMedicineInventorList());

        //initialize the medicine table with the two columns
        medicineNameColumn.setCellValueFactory((TableColumn.CellDataFeatures<Medicine, String> cellData) -> cellData.getValue().medicineNameProperty());
        medicineQuantityColumn.setCellValueFactory((TableColumn.CellDataFeatures<Medicine, Integer> cellData) -> cellData.getValue().quantityProperty().asObject());

        //clear medicine details
        showMedicineDetails(null);

        //double click item to add to checkout list
        medicineNameColumn.setCellFactory((TableColumn<Medicine, String> param) -> {
            TableCell<Medicine, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null)
                        setText(item);
                }
            };
            cell.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    Medicine selectedMedicine = medicineTable.getSelectionModel().selectedItemProperty().getValue();
                    MedicineHandler.getMedicineCheckOutList().add(selectedMedicine);
                }
            });
            return cell;
        });
        /*
        for integer and doubles
        myIntegerColumn.setCellValueFactory(cellData ->
      cellData.getValue().myIntegerProperty().asObject());
         */
        //listen for selection changes and show the medicine details when changed
        medicineTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showMedicineDetails(newValue));

        for (Medicine medicine : MedicineHandler.getMedicineCheckOutList()) {
            checkOutBox.getItems().add(medicine.getMedicineName());
        }
    }

    @FXML
    private void handleCheckout() {
        menuPage.handleSell();
    }

    //opens dialog to edit selected medicine details
    @FXML
    private void handleEdit() {
        Medicine selectedMedicine = medicineTable.getSelectionModel().getSelectedItem();
        if (selectedMedicine != null) {
            okClicked = showMedicineEditDialog(selectedMedicine);
            if (okClicked)
                showMedicineDetails(selectedMedicine);
        } else
            warningAlert();
    }

    @FXML
    private void handleDelete() {
        int selectedIndex = medicineTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0)
            medicineTable.getItems().remove(selectedIndex);
        else
            warningAlert();

    }

    @FXML
    private void handleAdd() {
        Medicine tempMedicine = new Medicine();
        okClicked = showMedicineEditDialog(tempMedicine);
        if (okClicked)
            MedicineHandler.getMedicineInventorList().add(tempMedicine);
    }

    @FXML
    private void handleBack() {
        loginPage.openMenu();
    }

    public void setMenuPage(MenuPage menuPage) {
        this.menuPage = menuPage;
        this.userAccess = menuPage.getUserAccess();

        if (!userAccess.equals("ADMIN"))
            editButton.setDisable(true);
        else
            editButton.setDisable(false);
    }

    private void showMedicineDetails(Medicine medicine) {
        if (medicine != null) {
            //fill the labels with info from the user object
            nameLabel.setText(medicine.getMedicineName());
            descriptionText.setText("The Description");
            quantityLabel.setText(String.valueOf(medicine.getQuantity()));
            priceLabel.setText(String.valueOf(medicine.getPrice()));
            entryDateLabel.setText(DateUtil.format(medicine.getEntryDate()));
        } else {
            nameLabel.setText("");
            descriptionText.setText("");
            quantityLabel.setText("");
            priceLabel.setText("");
            entryDateLabel.setText("");
        }
    }

    private boolean showMedicineEditDialog(Medicine medicine) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("EditDialog.fxml"));
            AnchorPane pane = loader.load();

            //create dialog Stage
            Stage editStage = new Stage();
            editStage.setTitle("Edit Item");
            editStage.initModality(Modality.WINDOW_MODAL);
            editStage.initOwner(main.getPrimaryStage());
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

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("View Page");
    }

    private void warningAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(main.getPrimaryStage());
        alert.setTitle("No Selection");
        alert.setHeaderText("No Medicine Selected");
        alert.setContentText("Please select an item in the table");
    }

    public void setLoginPage(LoginPage loginPage) {
        this.loginPage = loginPage;
    }

    public void setMain(Main main) {
        this.main = main;
    }

}
