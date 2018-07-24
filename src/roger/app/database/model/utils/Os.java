package roger.app.database.model.utils;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.HashSet;

public class Os {

    private final StringProperty name = new SimpleStringProperty();
    private final BooleanProperty delete = new SimpleBooleanProperty();

    public Os(String nm, boolean del) {
        name.set(nm);
        delete.set(del);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public BooleanProperty deleteProperty() {
        return delete;
    }
}

class FxEditableCheckBoxJava10 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        final var view = new TableView<Os>();
        final var columns = view.getColumns();
        final var nameColumn = new TableColumn<Os, Boolean>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        columns.add(nameColumn);
        final var loadedColumn = new TableColumn<Os, Boolean>("Delete");
        loadedColumn.setCellValueFactory(new PropertyValueFactory<>("delete"));
        loadedColumn.setCellFactory(tc -> new CheckBoxTableCell<>());
        columns.add(loadedColumn);
        final var items = FXCollections.observableArrayList(
                new Os("Microsoft Windows 3.1", true),
                new Os("Microsoft Windows 3.11", true),
                new Os("Microsoft Windows 95", true),
                new Os("Microsoft Windows NT 3.51", true),
                new Os("Microsoft Windows NT 4", true),
                new Os("Microsoft Windows 2000", true),
                new Os("Microsoft Windows Vista", true),
                new Os("Microsoft Windows Seven", false),
                new Os("Linux all versions :-)", false));
        view.setItems(items);
        view.setEditable(true);
        final var delBtn = new Button("Delete");
        delBtn.setMaxWidth(Double.MAX_VALUE);
        delBtn.setOnAction(e -> {
            final var del = new HashSet<Os>();
            for (final var os : view.getItems()) {
                if (os.deleteProperty().get()) {
                    del.add(os);
                }
            }
            view.getItems().removeAll(del);
        });
        stage.setScene(new Scene(new BorderPane(view, null, null, delBtn, null)));
        BorderPane.setAlignment(delBtn, Pos.CENTER);
        stage.show();
    }
}