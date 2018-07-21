package roger.app.database.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import roger.app.database.model.medicine.Medicine;
import roger.app.database.model.users.UserHandler;

import java.io.IOException;
import java.util.Objects;

public class MenuPage {

    private static ObservableList<Medicine> checkOutList = FXCollections.observableArrayList();

    private Stage primaryStage;

    private String userName;

    private String userAccess;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label userAccessLabel;

    @FXML
    private Button addUserButton;

    private Main main;

    private LoginPage loginPage;

    public static ObservableList<Medicine> getCheckOutList() {
        return checkOutList;
    }

    //called after fxml is loaded
    @FXML
    private void initialize() {
        this.userName = LoginPage.getUserName();
        this.userAccess = Objects.requireNonNull(UserHandler.getUserAccess(userName)).toUpperCase();
        userNameLabel.setText(userName);
        userAccessLabel.setText(userAccess);
        if (!userAccess.equals("ADMIN"))
            addUserButton.setDisable(true);
        else
            addUserButton.setDisable(false);
    }

    @FXML
    private void handleSell() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("SellPage.fxml"));
            AnchorPane pane = loader.load();

            SellPage sellPage = loader.getController();
            sellPage.setMain(main);

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
    private void handleView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ViewPage.fxml"));
            AnchorPane pane = loader.load();

            ViewPage viewPage = loader.getController();
            viewPage.setPrimaryStage(primaryStage);
            viewPage.setMain(main);
            viewPage.setMenuPage(this);
            viewPage.setLoginPage(loginPage);

            Scene scene = new Scene(pane);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout() {
        userName = null;
        new Main().start(primaryStage);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Menu Page");
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public String getUserAccess() {
        return userAccess;
    }

    public void setLoginPage(LoginPage loginPage) {
        this.loginPage = loginPage;
    }
}
