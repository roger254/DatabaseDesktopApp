package roger.app.database.model.users;

import java.util.ArrayList;

public abstract class UserHandler {

    private static ArrayList<User> users = new ArrayList<>();


    public static void addUsers(User user) {
        users.add(user);
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static String getUserAccess(String name) {
        for (User user : users) {
            if (user.getName().equals(name))
                return user.getAccess();
        }
        return null;
    }
}
