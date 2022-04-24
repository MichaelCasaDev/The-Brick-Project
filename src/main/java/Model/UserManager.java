package Model;

import java.util.ArrayList;

public class UserManager {
    private ArrayList<User> users;

    public UserManager() {
        users = new ArrayList<>();
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
}
