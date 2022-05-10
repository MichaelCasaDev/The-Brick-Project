package Model;

import Main.GlobalVars;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GlobalManager {
    private String lastUser;
    private String level0;

    private String path;

    public GlobalManager(String path) {
        this.path = path;

        loadData();
    }

    public String getLastUser() {
        return lastUser;
    }

    public String getLevel0() {
        return level0;
    }

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
