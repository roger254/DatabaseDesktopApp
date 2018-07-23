package roger.app.database.model.medicine;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MedicineHandler {

    private static Medicine medicine;
    private static ObservableList<Medicine> medicineInventorList = FXCollections.observableArrayList();
    private static ObservableList<Medicine> medicineCheckOutList = FXCollections.observableArrayList();
    private static int prevousAmount = 0;

    static {
        loadMedicineData();
    }

    private static void loadMedicineData() {
        //todo: load from saved file
        medicineInventorList.add(new Medicine("Piriton", 45.5, 1));
        medicineInventorList.add(new Medicine("Ken", 50, 2));
        medicineInventorList.add(new Medicine("Roger", 55.5, 6));
        medicineInventorList.add(new Medicine("FluGone", 67.5, 5));
    }

    public static void saveMedicineData() {
        //todo: save current medicine data to file
    }

    public static int getMedicineQuantity(Medicine medicine) {
        for (Medicine medicine1 : medicineInventorList) {
            if (medicine.getMedicineName().equals(medicine1.getMedicineName()))
                return medicine.getQuantity();
        }
        return -1;
    }

    public static ObservableList<Medicine> getMedicineInventorList() {
        return medicineInventorList;
    }

    public static ObservableList<Medicine> getMedicineCheckOutList() {
        return medicineCheckOutList;
    }

    public static void addToCheckOut(Medicine medicine, int amount) {
        prevousAmount = medicine.getQuantity();
        medicine.setQuantity(prevousAmount - amount);
        medicineCheckOutList.add(medicine);
    }

    public static void handleCancelCheckOut() {
        if (medicineCheckOutList.size() > 0)
            for (Medicine medicine : medicineCheckOutList) {
                if (medicine.getQuantity() == prevousAmount)
                    medicine.setQuantity(prevousAmount);
                else
                    medicineCheckOutList.removeAll();
            }
    }
}
