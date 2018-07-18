package roger.app.database.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import roger.app.database.model.users.UserHandler;

public class MenuPage {

    private String userName;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label userAccessLabel;

    //called after fxml is loaded
    @FXML
    private void initialize() {
        this.userName = LoginPage.getUserName();
        userNameLabel.setText(userName);
        userAccessLabel.setText(UserHandler.getUserAccess(userName).toUpperCase());
    }

    @FXML
    private void handleSell() {

    }

    @FXML
    private void handleView() {

    }

    @FXML
    private void handleStatistics() {

    }

    @FXML
    private void handleEdit() {

    }

    @FXML
    private void handleLogout() {
        userName = null;
    }
}
