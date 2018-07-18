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

import java.io.IOException;
import java.util.Optional;

public class LoginPage {

    private Stage primaryStage;
    private String userName;
    private String password;


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
            System.out.println("UserName=" + userNamePassword.getKey() + "Password=" + userNamePassword.getValue());
             validateUser();
        });

    }

    @FXML
    private void handleExit() {
        System.exit(1);
    }

    //TODO: write validating code
    private void validateUser() {
        openMenu();
    }

    private void openMenu() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("MenuPage.fxml"));
            AnchorPane pane = loader.load();
            //    menuStage.initModality(Modality.WINDOW_MODAL);
            // menuStage.initOwner(primaryStage);

            Scene scene = new Scene(pane);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
