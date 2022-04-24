package Model;

import javax.swing.*;
import java.util.ArrayList;

public class LevelManager {
    private ArrayList<Level> levels;

    public LevelManager() {
        levels = new ArrayList<>();
    }

    public void add(Level l) {
        levels.add(l);
    }

    public ArrayList<Level> getList() {
        return levels;
    }

    public DefaultListModel<Level> getListModel() {
        DefaultListModel<Level> model = new DefaultListModel<>();
        model.addAll(levels);
        return model;
    }
}
