package Model;

import Main.GlobalVars;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

/**
 * User
 */
public class User {
	private String uuid;
	private String username;
	private boolean sounds;
	private long totBricksBreak;
	private long totPlayGame;
	private String level;

	/**
	 * Constructor
	 */
	public User() {
		this.uuid = UUID.randomUUID().toString(); // Generate random UUID for new user
		this.username = "Player";
		this.sounds = true;
		this.totBricksBreak = 0;
		this.totPlayGame = 0;
		this.level = "9b9fd586-652c-42fa-bc8b-6bbaa2a39222";

		saveToJSON();
	}

	/**
	 * Constructor
	 * @param path the path of the file
	 */
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
			JOptionPane.showMessageDialog(null, "Errore - " + e.getMessage(), "Exception", JOptionPane.PLAIN_MESSAGE);
		}
	}

	/**
	 * Get user username
	 * @return user username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Get total bricks broke from the user
	 * @return total bricks broke
	 */
	public long getTotBricksBreak() {
		return totBricksBreak;
	}

	/**
	 * Get total played game from the user
	 * @return total play game
	 */
	public long getTotPlayGame() {
		return totPlayGame;
	}

	/**
	 * Get the current user level
	 * @return current user level
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * Change the user username
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.username = username;
		saveToJSON();
	}

	/**
	 * Change the user total bricks break
	 * @param totBricksBreak the new total user bricks break
	 */
	public void setTotBricksBreak(long totBricksBreak) {
		this.totBricksBreak = totBricksBreak;
		saveToJSON();
	}

	/**
	 * Change the total user played game
	 * @param totPlayGame the new total play game
	 */
	public void setTotPlayGame(long totPlayGame) {
		this.totPlayGame = totPlayGame;
		saveToJSON();
	}

	/**
	 * Change user current level
	 * @param level the new user current level
	 */
	public void setLevel(String level) {
		this.level = level;
		saveToJSON();
	}

	/**
	 * Change user sound preference
	 * @param sounds new sound check
	 */
	public void setSounds(boolean sounds) {
		this.sounds = sounds;
		saveToJSON();
	}

	/**
	 * Get user UUID
	 * @return user UUID
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * Get user sound preference
	 * @return user sound preference
	 */
	public boolean getSounds() {
		return sounds;
	}

	/**
	 * The to string method
	 * @return the string of the user
	 */
	@Override
	public String toString() {
		return username;
	}

	/**
	 * Save data to disk
	 */
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
			JOptionPane.showMessageDialog(null, "Errore - " + e.getMessage(), "Exception", JOptionPane.PLAIN_MESSAGE);
		}
	}
}
