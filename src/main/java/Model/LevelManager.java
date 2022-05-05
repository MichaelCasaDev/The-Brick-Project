package Model;

import Main.GlobalVars;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public class LevelManager {
    private ArrayList<Level> levels;

    public LevelManager() {
        levels = new ArrayList<>();

        loadData();
    }

    public void add(Level l) {
        levels.add(l);
    }

    public DefaultListModel<Level> getListModel() {
        DefaultListModel<Level> model = new DefaultListModel<>();
        model.addAll(levels);
        return model;
    }

    public Level parser(String uuid) {
        return new Level(GlobalVars.dirBase + "levels/" + uuid + ".json");
    }

    public void loadData() {
        final String dirUsers = GlobalVars.dirBase + "levels";
        File folder = new File(dirUsers);
        for(File file : folder.listFiles()) {
            add(new Level(file.getPath()));
        }
    }
}
