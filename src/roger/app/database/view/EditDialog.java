package roger.app.database.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import roger.app.database.model.medicine.Medicine;
import roger.app.database.model.utils.DateUtil;

public class EditDialog {

    @FXML
    private TextField medicineNameField;

    @FXML
    private TextField medicineQuantityField;

    @FXML
    private TextField medicinePriceField;

    @FXML
    private TextArea medicineDescriptionField;

    @FXML
    private TextField medicineEntryDateField;

    private Stage dialogStage;
    private Medicine medicine;
    private boolean okClicked = false;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;

        if (medicine != null) {
            medicineNameField.setText(medicine.getMedicineName());
            medicineQuantityField.setText(String.valueOf(medicine.getQuantity()));
            medicinePriceField.setText(String.valueOf(medicine.getPrice()));
            medicineDescriptionField.setText(medicine.getMedicineDescription());
            medicineEntryDateField.setText(DateUtil.format(medicine.getEntryDate()));
            medicineEntryDateField.setPromptText("dd.mm.yyyy");
        } else {
            medicineNameField.setPromptText("Medicine Name");
            medicineQuantityField.setPromptText("Quantity to be Entered");
            medicinePriceField.setPromptText("Price of Item");
            medicineDescriptionField.setPromptText("Special Notes");
            medicineEntryDateField.setText("");
            medicineEntryDateField.setPromptText("dd.mm.yyyy");
        }
    }

    public boolean isOkClicked() {
        return okClicked;
    }


    @FXML
    private void handleOk() {
        if (isInputValid()) {
            medicine.setMedicineName(medicineNameField.getText());
            medicine.setQuantity(Integer.parseInt(medicineQuantityField.getText()));
            medicine.setPrice(Double.parseDouble(medicinePriceField.getText()));
            medicine.setMedicineDescription(medicineDescriptionField.getText());
            medicine.setEntryDate(DateUtil.parse(medicineEntryDateField.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (medicineNameField.getText() == null || medicineNameField.getText().length() == 0)
            errorMessage += "No valid medicine name!\n";
        if (medicineDescriptionField.getText() == null || medicineDescriptionField.getText().length() == 0)
            errorMessage += "No valid medicine description!\n";
        //validate price
        if (medicinePriceField.getText() == null || medicinePriceField.getText().length() == 0)
            errorMessage += "No valid price!\n";
        else {
            //try to parse the price into an double
            try {
                Double.parseDouble(medicinePriceField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid price (must be an double)!\n";
            }
        }
        //validate quantity
        if (medicineQuantityField.getText() == null || medicineQuantityField.getText().length() == 0)
            errorMessage += "No valid quantity!\n";
        else {
            //try to parse the quantity into an int
            try {
                Integer.parseInt(medicineQuantityField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid quantity(must be an integer)!\n";
            }
        }
        if (medicineEntryDateField.getText() == null || medicineEntryDateField.getText().length() == 0)
            errorMessage += "No valid birthday!\n";
        else {
            if (!DateUtil.validDate(medicineEntryDateField.getText())) {
                errorMessage += "No valid entry date. Use the format dd.mm.yyyy!\n";
            }
        }

        if (errorMessage.length() == 0)
            return true;
        else {
            //show the error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
}
