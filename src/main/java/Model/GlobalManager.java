package Model;

import Main.GlobalVars;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GlobalManager {
    private String lastUser;
    private String level0;

    public GlobalManager(String path) {
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
            e.printStackTrace();
        }
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
            e.printStackTrace();
        }
    }
}
