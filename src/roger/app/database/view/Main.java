package roger.app.database.view;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        ViewHandler viewHandler = new ViewHandler(primaryStage);

        ViewHandler.loadLoginPage();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
