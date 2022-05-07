package Model;

import Main.GlobalVars;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class User {
	private String uuid;
	private String username;
	private boolean sounds;
	private long totBricksBreak;
	private long totPlayGame;
	private String level;

	public User() {
		this.uuid = UUID.randomUUID().toString(); // Generate random UUID for new user
		this.username = "Player";
		this.sounds = true;
		this.totBricksBreak = 0;
		this.totPlayGame = 0;
		this.level = "9b9fd586-652c-42fa-bc8b-6bbaa2a39222";

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
			boolean sounds = (boolean) jsonFile.get("sounds");
			long totBricksBreak = (long) jsonFile.get("totBricksBreak");
			long totPlayGame = (long) jsonFile.get("totPlayGame");
			String level = (String) jsonFile.get("level");

			/* ##############################
			 * Save data from parsed JSON File
			 * ############################## */
			this.uuid = uuid;
			this.username = username;
			this.sounds = sounds;
			this.totBricksBreak = totBricksBreak;
			this.totPlayGame = totPlayGame;
			this.level = level;


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

	public String getLevel() {
		return level;
	}

	public void setUsername(String username) {
		this.username = username;
		saveToJSON();
	}

	public void setTotBricksBreak(long totBricksBreak) {
		this.totBricksBreak = totBricksBreak;
		saveToJSON();
	}

	public void setTotPlayGame(long totPlayGame) {
		this.totPlayGame = totPlayGame;
		saveToJSON();
	}

	public void setLevel(String level) {
		this.level = level;
		saveToJSON();
	}

	public void setSounds(boolean sounds) {
		this.sounds = sounds;
		saveToJSON();
	}

	public String getUuid() {
		return uuid;
	}

	public boolean getSounds() {
		return sounds;
	}

	@Override
	public String toString() {
		return username;
	}

	private void saveToJSON() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("uuid", uuid);
		jsonObj.put("username", username);
		jsonObj.put("sounds", sounds);
		jsonObj.put("totBricksBreak", totBricksBreak);
		jsonObj.put("totPlayGame", totPlayGame);
		jsonObj.put("level", level);

		try (FileWriter file = new FileWriter(GlobalVars.dirBase + "users/" + uuid + ".json")) {
			file.write(jsonObj.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
