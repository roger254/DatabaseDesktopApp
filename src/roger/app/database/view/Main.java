package roger.app.database.view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import roger.app.database.model.medicine.Medicine;

import java.io.IOException;

public class Main extends Application {

    private ObservableList<Medicine> medicineData = FXCollections.observableArrayList();
    private Stage primaryStage;
    private Scene scene;

    public Main() {
        medicineData.add(new Medicine("Piriton", 45.5, 1));
        medicineData.add(new Medicine("Ken", 50, 2));
        medicineData.add(new Medicine("Roger", 55.5, 6));
        medicineData.add(new Medicine("FluGone", 67.5, 5));
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        loadLoginPage();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadLoginPage() {
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

    public ObservableList<Medicine> getMedicineData() {
        return medicineData;
    }

    public void setMedicineData(ObservableList<Medicine> medicineData) {
        this.medicineData = medicineData;
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
