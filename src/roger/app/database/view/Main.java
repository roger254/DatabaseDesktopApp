package roger.app.database.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;
    private Scene scene;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        loadLoginPage();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void loadLoginPage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("loginPage.fxml"));
            AnchorPane pane = loader.load();
            LoginPage loginPageController = loader.getController();
            loginPageController.setPrimaryStage(primaryStage);
            loginPageController.setMain(this);
            scene = setPageScene(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }


    public Scene setPageScene(Pane pane) {
        if (pane != null)
            return new Scene(pane);
        else
            return null;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
