package roger.app.database.view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import roger.app.database.model.medicine.Medicine;
import roger.app.database.model.users.User;
import roger.app.database.model.users.UserHandler;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

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
    public void handleSell() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("SellPage.fxml"));
            AnchorPane pane = loader.load();

            SellPage sellPage = loader.getController();
            sellPage.setMain(main);
            sellPage.setLoginPage(loginPage);

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
    private void handleAddUser() {
        //Create the custom dialog
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("New User Sign Up");
        dialog.setHeaderText("Please enter your user-name, password and user access level!");

        //TODO: seth the icon (must be included in the project).
        dialog.setGraphic(new ImageView(this.getClass().getResource("").toString()));

        //set the button types
        ButtonType loginButtonType = new ButtonType("Sign Up", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        //Create the userName and password labels and fields
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));
        TextField userName = new TextField();
        userName.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().add("ADMIN");
        choiceBox.getItems().add("REGULAR USER");

        gridPane.add(new Label("UserName:"), 0, 0);
        gridPane.add(userName, 1, 0);
        gridPane.add(new Label("Password:"), 0, 1);
        gridPane.add(password, 1, 1);
        gridPane.add(choiceBox, 2, 0);

        //Enable/Disable Login button depending on whether a username was entered
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        //do some validation
        userName.textProperty().addListener((observable, oldValue, newValue) -> loginButton.setDisable(newValue.trim().isEmpty()));

        dialog.getDialogPane().setContent(gridPane);

        //Request focus on the username field by default
        Platform.runLater(userName::requestFocus);

        //convert the result to a username-password-pair when the logon button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType)
                return new Pair<>(userName.getText(), password.getText());
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        //TODO validate the input for new user
        result.ifPresent(userNamePassword -> {
            String newUserName = userNamePassword.getKey();
            String newUserPassword = userNamePassword.getValue();
            UserHandler.addUsers(new User(newUserName, newUserPassword, choiceBox.getValue()));
        });

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
