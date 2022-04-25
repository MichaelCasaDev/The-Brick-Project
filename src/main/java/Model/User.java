package Model;

import Main.GlobalVars;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class User {
	private String uuid;
	private String username;
	private long totBricksBreak;
	private long totPlayGame;
	private ArrayList<String> level;

	public User() {
		this.uuid = UUID.randomUUID().toString(); // Generate random UUID for new user
		this.username = "Player0";
		this.totBricksBreak = 0;
		this.totPlayGame = 0;
		this.level = new ArrayList<>();

		saveToJSON();
	}

	public User(String path) {
		try (FileReader file = new FileReader(path)) {
			/* ##############################
			 * Retrieve data from JSON File
			 * ############################## */
			JSONParser parser = new JSONParser();
			JSONObject jsonFile = (JSONObject) parser.parse(file);
			String uuid = (String) jsonFile.get("uuid");
			String username = (String) jsonFile.get("username");
			long totBricksBreak = (long) jsonFile.get("totBricksBreak");
			long totPlayGame = (long) jsonFile.get("totPlayGame");
			JSONArray level = (JSONArray) jsonFile.get("level");

			/* ##############################
			 * Save data from parsed JSON File
			 * ############################## */
			this.uuid = uuid;
			this.username = username;
			this.totBricksBreak = totBricksBreak;
			this.totPlayGame = totPlayGame;

			for(Object l : level) {
				level.add(l.toString());
			}


		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public String getUsername() {
		return username;
	}

	public long getTotBricksBreak() {
		return totBricksBreak;
	}

	public long getTotPlayGame() {
		return totPlayGame;
	}

	public ArrayList<String> getLevel() {
		return level;
	}

	public void setUsername(String username) {
		this.username = username;
		saveToJSON();
	}

	public void setTotBricksBreak(int totBricksBreak) {
		this.totBricksBreak = totBricksBreak;
		saveToJSON();
	}

	public void setTotPlayGame(int totPlayGame) {
		this.totPlayGame = totPlayGame;
		saveToJSON();
	}

	public void setLevel(ArrayList<String> level) {
		this.level = level;
		saveToJSON();
	}

	private void saveToJSON() {
		JSONArray levelObj = new JSONArray();
		levelObj.addAll(level);

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("username", username);
		jsonObj.put("totBricksBreak", totBricksBreak);
		jsonObj.put("totPlayGame", totPlayGame);
		jsonObj.put("level", levelObj);

		try (FileWriter file = new FileWriter(GlobalVars.dirBase + "players/" + uuid + ".json")) {
			file.write(jsonObj.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
