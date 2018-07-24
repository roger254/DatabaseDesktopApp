package roger.app.database.view;

import javafx.application.Application;
import javafx.stage.Stage;
import roger.app.database.model.users.UserHandler;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        new ViewHandler(primaryStage);
        UserHandler.loadUsers();
        ViewHandler.loadLoginPage();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
