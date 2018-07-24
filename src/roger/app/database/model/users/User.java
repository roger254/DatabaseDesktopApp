package roger.app.database.model.users;

public class User {
    private String name;
    private String password;
    private String access;

    public User(String name, String password, String access) {
        this.name = name;
        this.password = password;
        this.access = access;
    }

    public User() {
        name = "";
        password = "";
        access = "";
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccess(String access) {
        this.access = access;
    }


    public String getName() {
        return name;
    }

    public String getPassword()
    {
        return password;
    }

    public String getAccess() {
        return access;
    }
}
