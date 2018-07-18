package roger.app.database.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;
    private Scene scene;
    private AnchorPane pane;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        loadLoginPage();
        scene = initStage();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadLoginPage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("loginPage.fxml"));
            pane = loader.load();
            LoginPage loginPageController = loader.getController();
            loginPageController.setPrimaryStage(primaryStage);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private Scene initStage() {
        return new Scene(pane);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
