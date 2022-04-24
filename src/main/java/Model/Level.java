package Model;

import Main.GlobalVars;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Level {
	private String uuid;
	private String name;
	private String path;
	private boolean done;
	private int totBricks;
	private int ballSpeed;
	private int[][] map;

	public Level(String path) {
		try (FileReader file = new FileReader(path)) {
			/* ##############################
			 * Retrieve data from JSON File
			 * ############################## */
			JSONParser parser = new JSONParser();
			JSONObject jsonFile = (JSONObject) parser.parse(file);
			JSONArray map = (JSONArray) jsonFile.get("map");
			JSONObject name = (JSONObject) jsonFile.get("name");
			JSONObject done = (JSONObject) jsonFile.get("done");
			JSONObject uuid = (JSONObject) jsonFile.get("uuid");
			JSONObject ballSpeed = (JSONObject) jsonFile.get("ballSpeed");
			JSONObject totBricks = (JSONObject) jsonFile.get("totBricks");

			/* ##############################
			 * Save data from parsed JSON File
			 * ############################## */
			this.uuid = uuid.toString();
			this.name = name.toString();
			this.done = done.toString().equals("true");
			this.path = path;
			this.totBricks = Integer.parseInt(totBricks.toString());
			this.ballSpeed = Integer.parseInt(ballSpeed.toString());
			this.map = new int[GlobalVars.gameRows][GlobalVars.gameCols];

			/* ##############################
			 * Generate map from JSON Array
			 * ############################## */
			int pos = 0;
			for (int i = 0; i < GlobalVars.gameRows; i++) {
				for (int j = 0; j < GlobalVars.gameCols; j++) {
					int block = Integer.parseInt(map.get(pos).toString());
					pos++;

					this.map[i][j] = block;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public String getUUID() {
		return uuid;
	}

	public String getName() {
		return name;
	}

	public boolean getDone() {
		return done;
	}

	public String getPath() {
		return path;
	}

	public int getTotBricks() {
		return totBricks;
	}

	public int getBallSpeed() {
		return ballSpeed;
	}

	public int[][] getMap() {
		return map;
	}

	public void setDone(boolean done) {
		this.done = done;
		saveToJSON();
	}

	@Override
	public String toString() {
		return name;
	}

	private void saveToJSON() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("uuid", uuid);
		jsonObj.put("name", name);
		jsonObj.put("totBricks", totBricks);
		jsonObj.put("ballSpeed", ballSpeed);
		jsonObj.put("map", map);
		jsonObj.put("done", done);

		try (FileWriter file = new FileWriter(path)) {
			file.write(jsonObj.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
