package Model;

import Main.GlobalVars;
import Main.MainRunnable;

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

    public void remove(int index) {
        users.remove(index);
    }

    public User get(int index) {
        return users.get(index);
    }

    public void update(User p, int index) {
        users.set(index, p);
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
