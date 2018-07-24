package roger.app.database.model.users;

import javafx.stage.FileChooser;
import roger.app.database.view.ViewHandler;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.prefs.Preferences;

public abstract class UserHandler {

    private static ArrayList<User> users = new ArrayList<>();
    private static User currentUser;

    public static void loadUsers() {

        File file = getUserFilePath();
        if (file != null)
            loadUserDataFromFile(file);
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

    /*
  Returns the person file preference i.e. the file that was last opened.
  the preference is read from the OS registry
   */
    private static File getUserFilePath() {
        Preferences preferences = Preferences.userNodeForPackage(UserHandler.class);
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
    private static void setUserFilePath(File file) {
        Preferences preferences = Preferences.userNodeForPackage(UserHandler.class);
        if (file != null)
            preferences.put("filePath", file.getPath());
        else
            preferences.remove("filePath");
    }

    //load medicine data from specified file
    private static void loadUserDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(UserHandler.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            //Reading from the file and unmarshalling
            UserWrapper userWrapper = (UserWrapper) unmarshaller.unmarshal(file);
            users.addAll(userWrapper.getUsers());

            //save the file path to the registry
            setUserFilePath(file);
        } catch (JAXBException e) {
            ViewHandler.showAlert(file);
        }
    }

    private static void saveUserDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(UserWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our person data.
            UserWrapper wrapper = new UserWrapper();
            wrapper.setUsers(users);

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);

            // Save the file path to the registry.
            setUserFilePath(file);
        } catch (Exception e) { // catches ANY exception
            ViewHandler.showAlert(file);
        }
    }

    public static void handleSave() {

        File userFilePath = UserHandler.getUserFilePath();
        if (userFilePath != null) {
            UserHandler.saveUserDataToFile(userFilePath);
        } else {
            FileChooser fileChooser = new FileChooser();

            // Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                    "XML files (*.xml)", "*.xml");
            fileChooser.getExtensionFilters().add(extFilter);

            // Show save file dialog
            File file = fileChooser.showSaveDialog(ViewHandler.getPrimaryStage());

            if (file != null) {
                // Make sure it has the correct extension
                if (!file.getPath().endsWith(".xml")) {
                    file = new File(file.getPath() + ".xml");
                }
                UserHandler.saveUserDataToFile(file);
            }
        }
    }
}
