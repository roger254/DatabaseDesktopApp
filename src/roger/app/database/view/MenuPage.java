package roger.app.database.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import roger.app.database.model.medicine.MedicineHandler;
import roger.app.database.model.users.User;
import roger.app.database.model.users.UserHandler;

import java.io.File;
import java.util.Objects;
import java.util.Optional;

public class MenuPage {

    @FXML
    private Label userNameLabel;

    @FXML
    private Label userAccessLabel;

    @FXML
    private Button addUserButton;

    //called after fxml is loaded
    @FXML
    private void initialize() {
        userNameLabel.setText(UserHandler.getCurrentUserName());
        userAccessLabel.setText(UserHandler.getCurrentUserAccess());
        if (!Objects.requireNonNull(UserHandler.getCurrentUserAccess()).equals("ADMIN"))
            addUserButton.setDisable(true);
        else
            addUserButton.setDisable(false);
    }

    @FXML
    public void handleSell() {
        ViewHandler.loadSellPage();
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
    public void handleView() {
        ViewHandler.loadViewPage();
    }

    @FXML
    private void handleLogout() {
        UserHandler.logoutUser();
        MedicineHandler.handleCancelCheckOut();
        MedicineHandler.getMedicineCheckOutList().removeAll(MedicineHandler.getMedicineCheckOutList()); //clear inventory after selling
        handleSave();
        ViewHandler.loadLoginPage();
    }

    private void handleSave() {
        File medicineFile = MedicineHandler.getMedicineFilePath();
        if (medicineFile != null) {
            MedicineHandler.saveMedicineDataToFile(medicineFile);
        } else {
            handleSaveAs();
        }
    }

    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(ViewHandler.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            MedicineHandler.saveMedicineDataToFile(file);
        }
    }
}
