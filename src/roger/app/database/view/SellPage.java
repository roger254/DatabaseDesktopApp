package roger.app.database.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import roger.app.database.model.medicine.Medicine;
import roger.app.database.model.medicine.MedicineHandler;

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

    private Main main;

    private MenuPage menuPage;

    @FXML
    private LoginPage loginPage;

    @FXML
    private void initialize() {
        medicineTableView.setItems(MedicineHandler.getMedicineCheckOutList());
        nameTableColumn.setCellValueFactory((cellData) -> cellData.getValue().medicineNameProperty());
        priceTableColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        quantityTableColumn.setCellValueFactory(cellData -> cellData.getValue().quantityNameProperty());
    }

    @FXML
    private void handleSell() {
        for (Medicine medicine : MedicineHandler.getMedicineCheckOutList()) { //loop through checkout list
            if (medicine.getQuantity() > 1) { //if the quantity is more than 1
                int currentQuantity = medicine.getQuantity() - 1; //remove one from the quantity
                medicine.setQuantity(currentQuantity); //update quantity
            } else if (medicine.getQuantity() <= 1) { //if quantity is one remove from inventory
                MedicineHandler.getMedicineInventorList().remove(medicine); //remove it from inventory
            }
        }
        MedicineHandler.getMedicineCheckOutList().removeAll(MedicineHandler.getMedicineCheckOutList()); //clear inventory after selling
    }

    @FXML
    private void handleBack() {
        loginPage.openMenu();
    }

    @FXML
    private void handleToView() {
        menuPage.handleSell();
    }

    public void setMain(Main main) {
        this.main = main;
        if (MedicineHandler.getMedicineCheckOutList().size() < 1)
            sellButton.setDisable(true);
        else
            sellButton.setDisable(false);
    }

    public void setLoginPage(LoginPage loginPage) {
        this.loginPage = loginPage;
    }

    public void setMenuPage(MenuPage menuPage) {
        this.menuPage = menuPage;
    }
}
