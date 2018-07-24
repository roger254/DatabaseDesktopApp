package roger.app.database.model.medicine;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.prefs.Preferences;

public class MedicineHandler {

    private static ObservableList<Medicine> medicineInventorList = FXCollections.observableArrayList();
    private static ObservableList<Medicine> medicineCheckOutList = FXCollections.observableArrayList();


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
                if (medicine.getQuantity() == medicine.getPrevoiusQuantity())
                    medicine.setQuantity(medicine.getPrevoiusQuantity());
                else
                    medicineCheckOutList.removeAll();
            }
    }

    /*
    Returns the person file preference i.e. the file that was last opened.
    the preference is read from the OS registry
     */
    public static File getMedicineFilePath() {
        Preferences preferences = Preferences.userNodeForPackage(MedicineHandler.class);
        String filePath = preferences.get("filePath", null);
        if (filePath != null)
            return new File(filePath);
        else
            return null;
    }

    /*
    Sets the file path of the currently loaded file
    is persisted in the os specific registry
     */
    public static void setMedicineFilePath(File file) {
        Preferences preferences = Preferences.userNodeForPackage(MedicineHandler.class);
        if (file != null)
            preferences.put("filePath", file.getPath());
        else
            preferences.remove("filePath");
    }

    //load medicine data from specified file
    public static void loadMedicineDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(MedicineWrapper.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            //Reading from the file and unmarshalling
            MedicineWrapper medicineWrapper = (MedicineWrapper) unmarshaller.unmarshal(file);
            medicineInventorList.clear();
            medicineInventorList.addAll(medicineWrapper.getMedicines());

            //save the file path to the registry
            setMedicineFilePath(file);
        } catch (JAXBException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from file:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    public static void saveMedicineDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(MedicineWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our person data.
            MedicineWrapper wrapper = new MedicineWrapper();
            wrapper.setMedicines(medicineInventorList);

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);

            // Save the file path to the registry.
            setMedicineFilePath(file);
        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file:\n" + file.getPath());

            alert.showAndWait();
        }
    }
}