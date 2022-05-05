package Model;

import Main.GlobalVars;

import java.io.File;
import java.util.ArrayList;

public class UserManager {
    private ArrayList<User> users;

    public UserManager() {
        users = new ArrayList<>();

        loadData();
    }

    public void add(User p) {
        users.add(p);
    }

    public void remove(User user) {
        new File(GlobalVars.dirBase + "users/" + user.getUuid() + ".json").delete();

        users.remove(user);
    }

    public User get(String uuid) {
        User user = null;
        for(User u : users) {
            if(u.getUuid().equals(uuid)) {
                user = u;
            }
        }
        return user;
    }

    public ArrayList<User> getList() {
        return users;
    }

    public void loadData() {
        final String dirUsers = GlobalVars.dirBase + "users";
        File folder = new File(dirUsers);

        for(File file : folder.listFiles()) {
            add(new User(file.getPath()));
        }
    }
}
