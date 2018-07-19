package roger.app.database.view;

import javafx.application.Platform;
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
import roger.app.database.model.users.User;
import roger.app.database.model.users.UserHandler;

import java.io.IOException;
import java.util.Optional;

public class LoginPage {

    private Stage primaryStage;
    private static String userName;
    private Main main;

    public static String getUserName() {
        return userName;
    }

    @FXML
    private void handleExit() {
        System.exit(1);
    }

    @FXML
    private void showLoginDialog() {
        //Create the custom dialog
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Login Dialog");
        dialog.setHeaderText("Please enter your user-name and password");

        //TODO: seth the icon (must be included in the project).
        dialog.setGraphic(new ImageView(this.getClass().getResource("").toString()));

        //set the button types
        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
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

        gridPane.add(new Label("UserName:"), 0, 0);
        gridPane.add(userName, 1, 0);
        gridPane.add(new Label("Password:"), 0, 1);
        gridPane.add(password, 1, 1);

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

        result.ifPresent(userNamePassword -> {
            if (validateUser(userNamePassword.getKey(), userNamePassword.getValue()))
                openMenu();
            else
                showLoginDialog();
        });

    }

    //TODO: write validating code
    private boolean validateUser(String name, String password) {
        UserHandler.addUsers(new User("Roger", "roger254", "admin"));
        UserHandler.addUsers(new User("ken", "ken254", "regularUser"));
        for (User user : UserHandler.getUsers()) {
            if (user.getName().equals(name) && user.getPassword().equals(password)) {
                userName = name;
                return true;
            }
        }
        return false;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    //open menu page
    private void openMenu() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("MenuPage.fxml"));
            AnchorPane pane = loader.load();
            //    menuStage.initModality(Modality.WINDOW_MODAL);
            // menuStage.initOwner(primaryStage);

            MenuPage menuPage = loader.getController();
            menuPage.setPrimaryStage(primaryStage);
            menuPage.setMain(main);

            primaryStage.setScene(new Scene(pane));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
