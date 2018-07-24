package roger.app.database.model.users;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

//save medicine data to xml
@XmlRootElement(name = "users")
class UserWrapper {

    private List<User> users;

    @XmlElement(name = "user")
    List<User> getUsers() {
        return users;
    }

    void setUsers(List<User> users) {
        this.users = users;
    }

}
