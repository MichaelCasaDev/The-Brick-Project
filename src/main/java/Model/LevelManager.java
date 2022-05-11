package Model;

import Main.GlobalVars;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

/**
 * The level manager
 */
public class LevelManager {
    private final ArrayList<Level> levels;

    /**
     * Constructor
     */
    public LevelManager() {
        levels = new ArrayList<>();

        loadData();
    }

    /**
     * Add a new level
     * @param l the level object
     */
    public void add(Level l) {
        levels.add(l);
    }

    /**
     * Get the list formatted for a JList object
     * @return the list for a JList
     */
    public DefaultListModel<Level> getListModel() {
        DefaultListModel<Level> model = new DefaultListModel<>();
        model.addAll(levels);
        return model;
    }

    /**
     * Return a level from his UUID
     * @param uuid
     * @return level object
     */
    public Level parser(String uuid) {
        return new Level(GlobalVars.dirBase + "levels/" + uuid + ".json");
    }

    /**
     * Load the data from the disk
     */
    public void loadData() {
        levels.clear();

        final String dirUsers = GlobalVars.dirBase + "levels";
        File folder = new File(dirUsers);
        for(File file : folder.listFiles()) {
            add(new Level(file.getPath()));
        }
    }
}
