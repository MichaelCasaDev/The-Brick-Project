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
	private int totBricksBreak;
	private int totPlayGame;
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
			JSONObject uuid = (JSONObject) jsonFile.get("uuid");
			JSONObject username = (JSONObject) jsonFile.get("username");
			JSONObject totBricksBreak = (JSONObject) jsonFile.get("totBricksBreak");
			JSONObject totPlayGame = (JSONObject) jsonFile.get("totPlayGame");
			JSONArray level = (JSONArray) jsonFile.get("level");

			/* ##############################
			 * Save data from parsed JSON File
			 * ############################## */
			this.uuid = uuid.toString();
			this.username = username.toString();
			this.totBricksBreak = Integer.parseInt(totBricksBreak.toString());
			this.totPlayGame = Integer.parseInt(totPlayGame.toString());

			for(Object l : level) {
				level.add(l.toString());
			}


		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public User(String username, int totBricksBreak, int totPlayGame, ArrayList<String> level) {
		this.username = username;
		this.totBricksBreak = totBricksBreak;
		this.totPlayGame = totPlayGame;
		this.level = level;

		saveToJSON();
	}

	public String getUsername() {
		return username;
	}

	public int getTotBricksBreak() {
		return totBricksBreak;
	}

	public int getTotPlayGame() {
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
