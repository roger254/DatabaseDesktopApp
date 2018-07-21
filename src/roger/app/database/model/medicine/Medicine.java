package roger.app.database.model.medicine;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Medicine {
    private final StringProperty medicineName;
    private final DoubleProperty price;
    private final IntegerProperty quantity;
    private final ObjectProperty<LocalDate> entryDate;
    private final StringProperty medicineDescription;

    public Medicine() {
        medicineName = new SimpleStringProperty("");
        medicineDescription = new SimpleStringProperty("");
        price = new SimpleDoubleProperty(0.0);
        quantity = new SimpleIntegerProperty(0);
        entryDate = new SimpleObjectProperty<>(LocalDate.now());
    }

    public Medicine(String medicineName, double price, int quantity) {
        this.medicineName = new SimpleStringProperty(medicineName);
        this.price = new SimpleDoubleProperty(price);
        this.quantity = new SimpleIntegerProperty(quantity);
        //TODO:Change all these variables
        String medicineDescription = "Med description";
        this.medicineDescription = new SimpleStringProperty(medicineDescription);
        int year = 2000, month = 10, date = 12;
        this.entryDate = new SimpleObjectProperty<>(LocalDate.of(year, month, date));
    }

    public StringProperty quantityNameProperty() {
        return new SimpleStringProperty(String.valueOf(getQuantity()));
    }

    public String getMedicineName() {
        return medicineName.get();
    }

    public void setMedicineName(String medicineName) {
        this.medicineName.set(medicineName);
    }

    public StringProperty medicineNameProperty() {
        return medicineName;
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public LocalDate getEntryDate() {
        return entryDate.get();
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate.set(entryDate);
    }

    public ObjectProperty<LocalDate> entryDateProperty() {
        return entryDate;
    }

    public String getMedicineDescription() {
        return medicineDescription.get();
    }

    public void setMedicineDescription(String medicineDescription) {
        this.medicineDescription.set(medicineDescription);
    }

    public StringProperty medicineDescriptionProperty() {
        return medicineDescription;
    }
}
