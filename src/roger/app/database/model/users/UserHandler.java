package roger.app.database.model.users;

import java.util.ArrayList;

public abstract class UserHandler {

    private static ArrayList<User> users = new ArrayList<>();
    private static User currentUser;

    static {
        loadUsers();
        addUsers(new User("Roger", "roger254", "ADMIN"));
        addUsers(new User("Ken", "ken254", "RegularUser"));
        saveUserData();
    }

    private static void saveUserData() {
        //todo: save user data to file
    }

    private static void loadUsers() {
        //TODO load user from saved file
    }

    public static boolean validateUser(String userName, String userPassword) {
        for (User user : users) {
            if (user.getName().equals(userName) && user.getPassword().equals(userPassword)) {
                currentUser = user;
                return true;
            }
        }
        return false;
    }

    public static void addUsers(User user) {
        users.add(user);
    }

    public static String getCurrentUserAccess() {
        if (currentUser != null)
            return currentUser.getAccess();
        return null;
    }

    public static String getCurrentUserName() {
        if (currentUser != null)
            return currentUser.getName();
        return null;
    }

    public static void logoutUser() {
        currentUser = null;
    }

}
