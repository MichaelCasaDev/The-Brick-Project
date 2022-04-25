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
	private long totBricks;
	private long breakBricks;
	private long ballSpeed;
	private int[][] map;
	private JSONArray mapJSON;

	public Level(String path) {
		try (FileReader file = new FileReader(path)) {
			/* ##############################
			 * Retrieve data from JSON File
			 * ############################## */
			JSONParser parser = new JSONParser();
			JSONObject jsonFile = (JSONObject) parser.parse(file);

			JSONArray map = (JSONArray) jsonFile.get("map");
			String name = (String) jsonFile.get("name");
			boolean done = (boolean) jsonFile.get("done");
			String uuid = (String) jsonFile.get("uuid");
			long ballSpeed = (long) jsonFile.get("ballSpeed");
			long totBricks = (long) jsonFile.get("totBricks");

			/* ##############################
			 * Save data from parsed JSON File
			 * ############################## */
			this.uuid = uuid;
			this.name = name;
			this.done = done;
			this.path = path;
			this.totBricks = totBricks;
			this.breakBricks = totBricks;
			this.ballSpeed = ballSpeed;
			this.map = new int[GlobalVars.gameRows][GlobalVars.gameCols];
			this.mapJSON = map;

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

	public long getTotBricks() {
		return totBricks;
	}

	public long getBallSpeed() {
		return ballSpeed;
	}

	public int[][] getMap() {
		return map;
	}

	public void setDone(boolean done) {
		this.done = done;
		saveToJSON();
	}

	public void removeBrick() {
		breakBricks--;
	}

	public long getBreakBricks() {
		return breakBricks;
	}

	@Override
	public String toString() {
		return name + " - " + (done ? "completato" : "non completato");
	}

	private void saveToJSON() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("uuid", uuid);
		jsonObj.put("name", name);
		jsonObj.put("totBricks", totBricks);
		jsonObj.put("ballSpeed", ballSpeed);
		jsonObj.put("map", mapJSON);
		jsonObj.put("done", done);

		try (FileWriter file = new FileWriter(path)) {
			file.write(jsonObj.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
