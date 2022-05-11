package Model;

import Main.GlobalVars;

import java.io.File;
import java.util.ArrayList;

/**
 * User manager
 */
public class UserManager {
    private final ArrayList<User> users;

    /**
     * Constructor
     */
    public UserManager() {
        users = new ArrayList<>();

        loadData();
    }

    /**
     * Add a new user
     * @param p new user object
     */
    public void add(User p) {
        users.add(p);
    }

    /**
     * Remove a user
     * @param user user Object
     */
    public void remove(User user) {
        new File(GlobalVars.dirBase + "users/" + user.getUuid() + ".json").delete();

        users.remove(user);
    }

    /**
     * Get a user from his UUID
     * @param uuid user UUID
     * @return the user Object found
     */
    public User get(String uuid) {
        User user = null;
        for(User u : users) {
            if(u.getUuid().equals(uuid)) {
                user = u;
            }
        }
        return user;
    }

    /**
     * Get the user arraylist
     * @return the user arraylist
     */
    public ArrayList<User> getList() {
        return users;
    }

    /**
     * Load data from disk
     */
    public void loadData() {
        users.clear();
        
        final String dirUsers = GlobalVars.dirBase + "users";
        File folder = new File(dirUsers);

        for(File file : folder.listFiles()) {
            add(new User(file.getPath()));
        }
    }
}
