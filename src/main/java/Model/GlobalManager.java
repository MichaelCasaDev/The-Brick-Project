package Model;

import Main.GlobalVars;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GlobalManager {
    private String lastUser;

    public GlobalManager(String path) {
        try (FileReader file = new FileReader(path)) {
            /* ##############################
             * Retrieve data from JSON File
             * ############################## */
            JSONParser parser = new JSONParser();
            JSONObject jsonFile = (JSONObject) parser.parse(file);
            String lastUser = (String) jsonFile.get("lastUser");

            /* ##############################
             * Save data from parsed JSON File
             * ############################## */
            this.lastUser = lastUser;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getLastUser() {
        return lastUser;
    }

    public void setLastUser(User user) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("lastUser", user.getUuid());

        try (FileWriter file = new FileWriter(GlobalVars.dirBase + "global.json")) {
            file.write(jsonObj.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
