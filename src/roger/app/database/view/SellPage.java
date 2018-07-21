package roger.app.database.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import roger.app.database.model.medicine.Medicine;

public class SellPage {

    @FXML
    private TableView<Medicine> medicineTableView;

    @FXML
    private TableColumn<Medicine, String> nameTableColumn;

    @FXML
    private TableColumn<Medicine, Double> priceTableColumn;

    @FXML
    private TableColumn<Medicine, String> quantityTableColumn;

    @FXML
    private Button sellButton;

    @FXML
    private Main main;

    @FXML
    private void initialize() {
        nameTableColumn.setCellValueFactory((cellData) -> cellData.getValue().medicineNameProperty());
        priceTableColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        quantityTableColumn.setCellValueFactory(cellData -> cellData.getValue().quantityNameProperty());
    }

    @FXML
    private void handleSell() {

    }

    @FXML
    private void handleBack() {

    }

    public void setMain(Main main) {
        this.main = main;
        medicineTableView.setItems(main.getMedicineData());
    }
}
