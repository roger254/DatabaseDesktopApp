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
    private LoginPage loginPage;

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
        loginPage.openMenu();
    }

    public void setMain(Main main) {
        this.main = main;
        medicineTableView.setItems(MenuPage.getCheckOutList());
        if (MenuPage.getCheckOutList().size() < 1)
            sellButton.setDisable(true);
        else
            sellButton.setDisable(false);
    }

    public void setLoginPage(LoginPage loginPage) {
        this.loginPage = loginPage;
    }
}
