package roger.app.database.model.medicine;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

//save medicine data to xml
@XmlRootElement(name = "medicines")
class MedicineWrapper {

    private List<Medicine> medicines;

    @XmlElement(name = "medicine")
    List<Medicine> getMedicines() {
        return medicines;
    }

    void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
    }

}
