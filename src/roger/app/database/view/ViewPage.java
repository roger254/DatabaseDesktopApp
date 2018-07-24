package roger.app.database.view;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
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

    @FXML
    private ComboBox<Medicine> checkOutBox;

    private boolean okClicked = false;

    //called after fxml file has been loaded
    @FXML
    private void initialize() {

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


        checkOutColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Medicine, CheckBox>, ObservableValue<CheckBox>>() {
            @Override
            public ObservableValue<CheckBox> call(
                    TableColumn.CellDataFeatures<Medicine, CheckBox> arg0) {
                final Medicine[] medicine = {arg0.getValue()};
                CheckBox checkBox = new CheckBox();
                checkBox.selectedProperty().setValue(medicine[0].isCheckOut());
                checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
                        medicine[0].setCheckOut(new_val);
                        medicine[0] = medicineTable.getSelectionModel().selectedItemProperty().get();
                        MedicineHandler.addToCheckOut(medicine[0], ViewHandler.amountPrompt(medicine[0]));
                    }
                });
                return new SimpleObjectProperty<CheckBox>(checkBox);
            }
        });


        //checkOutColumn.setCellFactory(new SimpleObjectProperty<Boolean>(false));
        showMedicineDetails(null);

        //listen for selection changes and show the medicine details when changed
        medicineTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showMedicineDetails(newValue));

        checkOutBox.getItems().addAll(MedicineHandler.getMedicineCheckOutList());

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
