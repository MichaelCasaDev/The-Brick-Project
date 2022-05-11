package Model;

import Main.GlobalVars;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Manager of the global.json file
 */
public class GlobalManager {
    private String lastUser;
    private String level0;

    private String path;

    /**
     * Constructor
     * @param path the path of the file
     */
    public GlobalManager(String path) {
        this.path = path;

        loadData();
    }

    /**
     * Get the last user UUID
     * @return last user UUID
     */
    public String getLastUser() {
        return lastUser;
    }

    /**
     * Get the first level of the game
     * @return first level UUID
     */
    public String getLevel0() {
        return level0;
    }

    /**
     * Change the last user
     * @param user the new user
     */
    public void setLastUser(User user) {
        lastUser = user.getUuid();

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("lastUser", lastUser);
        jsonObj.put("level0", level0);

        try (FileWriter file = new FileWriter(GlobalVars.dirBase + "global.json")) {
            file.write(jsonObj.toJSONString());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Errore - " + e.getMessage(), "Exception", JOptionPane.PLAIN_MESSAGE);
        }
    }

    /**
     * Load data from disk
     */
    public void loadData() {
        this.lastUser = "";
        this.level0 = "";

        try (FileReader file = new FileReader(path)) {
            /* ##############################
             * Retrieve data from JSON File
             * ############################## */
            JSONParser parser = new JSONParser();
            JSONObject jsonFile = (JSONObject) parser.parse(file);
            String lastUser = (String) jsonFile.get("lastUser");
            String level0 = (String) jsonFile.get("level0");

            /* ##############################
             * Save data from parsed JSON File
             * ############################## */
            this.lastUser = lastUser;
            this.level0 = level0;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Errore - " + e.getMessage(), "Exception", JOptionPane.PLAIN_MESSAGE);
        }
    }
}
