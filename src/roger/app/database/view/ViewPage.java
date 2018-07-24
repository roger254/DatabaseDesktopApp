package roger.app.database.view;

import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import roger.app.database.model.medicine.Medicine;
import roger.app.database.model.medicine.MedicineHandler;
import roger.app.database.model.users.UserHandler;
import roger.app.database.model.utils.DateUtil;

import java.util.Objects;

public class ViewPage {

    @FXML
    private TableView<Medicine> medicineTable;
    @FXML
    private TableColumn<Medicine, String> medicineNameColumn;
    @FXML
    private TableColumn<Medicine, Integer> medicineQuantityColumn;
    @FXML
    private TableColumn<Medicine, CheckBox> checkOutColumn;

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

    private boolean okClicked = false;

    private int amount;

    //called after fxml file has been loaded
    @FXML
    private void initialize() {
        initTable();
    }

    @FXML
    private void handleRefresh() {
        MedicineHandler.restoreDetails();
        initTable();
    }

    @FXML
    private void handleCheckout() {
        ViewHandler.loadSellPage();
    }

    //opens dialog to edit selected medicine details
    @FXML
    private void handleEdit() {
        Medicine selectedMedicine = medicineTable.getSelectionModel().getSelectedItem();
        if (selectedMedicine != null) {
            okClicked = ViewHandler.showMedicineEditDialog(selectedMedicine);
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
        okClicked = ViewHandler.showMedicineEditDialog(tempMedicine);
        if (okClicked)
            MedicineHandler.getMedicineInventorList().add(tempMedicine);
    }

    @FXML
    private void handleBack() {
        ViewHandler.loadMenuPage();
    }

    private void initTable() {
        //initialize data table
        medicineTable.setItems(MedicineHandler.getMedicineInventorList());

        //initialize the medicine table with the two columns
        medicineNameColumn.setCellValueFactory((TableColumn.CellDataFeatures<Medicine, String> cellData) -> cellData.getValue().medicineNameProperty());
        medicineQuantityColumn.setCellValueFactory((TableColumn.CellDataFeatures<Medicine, Integer> cellData) -> cellData.getValue().quantityProperty().asObject());

        checkOutColumn.setCellValueFactory(arg0 -> {
            Medicine medicine = arg0.getValue();
            CheckBox checkBox = new CheckBox();
            checkBox.setSelected(false);
            checkBox.selectedProperty().setValue(medicine.isCheckOut());
            checkBox.selectedProperty().addListener((ov, old_val, new_val) -> medicine.setCheckOut(new_val));
            checkBox.setOnAction(event -> {
                if (checkBox.isSelected()) {
                    amount = ViewHandler.amountPrompt(medicine);
                    MedicineHandler.addToCheckOut(medicine, amount);
                }
            });
            return new SimpleObjectProperty<>(checkBox);
        });

        //checkOutColumn.setCellFactory(new SimpleObjectProperty<Boolean>(false));
        showMedicineDetails(null);

        //listen for selection changes and show the medicine details when changed
        medicineTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showMedicineDetails(newValue));

        if (!Objects.requireNonNull(UserHandler.getCurrentUserAccess()).equals("ADMIN"))
            editButton.setDisable(true);
        else
            editButton.setDisable(false);

        //double click item to add to checkout list
        //  medicineNameColumn.setCellFactory(this::doubleClick);
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

    private void warningAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        //   alert.initOwner(main.getPrimaryStage());
        alert.setTitle("No Selection");
        alert.setHeaderText("No Medicine Selected");
        alert.setContentText("Please select an item in the table");
    }
}