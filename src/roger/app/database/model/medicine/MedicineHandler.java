package roger.app.database.model.medicine;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MedicineHandler {

    private static Medicine medicine;
    private static ObservableList<Medicine> medicineInventorList = FXCollections.observableArrayList();
    private static ObservableList<Medicine> medicineCheckOutList = FXCollections.observableArrayList();

    static {
        //todo:Load from saved file
        medicineInventorList.add(new Medicine("Piriton", 45.5, 1));
        medicineInventorList.add(new Medicine("Ken", 50, 2));
        medicineInventorList.add(new Medicine("Roger", 55.5, 6));
        medicineInventorList.add(new Medicine("FluGone", 67.5, 5));
    }

    public static ObservableList<Medicine> getMedicineInventorList() {
        return medicineInventorList;
    }

    public static ObservableList<Medicine> getMedicineCheckOutList() {
        return medicineCheckOutList;
    }

}
