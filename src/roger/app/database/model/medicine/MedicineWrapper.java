package roger.app.database.model.medicine;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

//save medicine data to xml
@XmlRootElement(name = "medicines")
public class MedicineWrapper {

    private List<Medicine> medicines;

    @XmlElement(name = "medicine")
    public List<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
    }

}
