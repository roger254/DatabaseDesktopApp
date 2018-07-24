package roger.app.database.model.medicine;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MedicineHandler {

    private static Medicine medicine;
    private static ObservableList<Medicine> medicineInventorList = FXCollections.observableArrayList();
    private static ObservableList<Medicine> medicineCheckOutList = FXCollections.observableArrayList();
    private static int previousQuantity = 0;

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
        medicineCheckOutList.removeIf(medicine1 -> !medicine1.isCheckOut());
        return medicineCheckOutList;
    }

    public static void addToCheckOut(Medicine medicine, int amount) {
        medicine.setQuantityToSell(amount);
        medicine.setToBeSold(true);
        medicine.setPrevoiusQuantity(medicine.getQuantity());
        if (medicine.isToBeSold())
            medicine.setQuantity(medicine.getPrevoiusQuantity() - amount);
        medicineCheckOutList.add(medicine);
    }

    public static void restoreDetails() {
        if (medicineCheckOutList.size() > 0) {
            for (Medicine medicine : medicineCheckOutList) {
                if (!medicine.isCheckOut()) {
                    medicine.setQuantity(medicine.getPrevoiusQuantity());
                }
            }
        }
        medicineCheckOutList.removeIf(medicine1 -> !medicine1.isCheckOut());
    }

    public static void handleCancelCheckOut() {
        if (medicineCheckOutList.size() > 0)
            for (Medicine medicine : medicineCheckOutList) {
                if (medicine.getQuantity() == previousQuantity)
                    medicine.setQuantity(previousQuantity);
                else
                    medicineCheckOutList.removeAll();
            }
    }
}
