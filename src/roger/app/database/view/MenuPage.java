package roger.app.database.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import roger.app.database.model.users.UserHandler;

import java.io.IOException;

public class MenuPage {


    private Stage primaryStage;

    private String userName;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label userAccessLabel;

    private Main main;

    public void setMain(Main main) {
        this.main = main;
    }

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
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ViewPage.fxml"));
            AnchorPane pane = loader.load();

            ViewPage viewPage = loader.getController();
            viewPage.setPrimaryStage(primaryStage);
            viewPage.setMain(main);

            Scene scene = new Scene(pane);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
