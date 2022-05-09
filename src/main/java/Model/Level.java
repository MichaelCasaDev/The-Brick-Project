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
	private String next_uuid;
	private String name;
	private long paddleSpeed;
	private long breakBricks;
	private long ballSpeed;
	private long bestTime;
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
			String uuid = (String) jsonFile.get("uuid");
			String next_uuid = (String) jsonFile.get("next_uuid");
			long ballSpeed = (long) jsonFile.get("ballSpeed");
			long bestTime = (long) jsonFile.get("bestTime");
			long paddleSpeed = (long) jsonFile.get("paddleSpeed");

			/* ##############################
			 * Save data from parsed JSON File
			 * ############################## */
			this.uuid = uuid;
			this.next_uuid = next_uuid;
			this.name = name;
			this.ballSpeed = ballSpeed;
			this.bestTime = bestTime;
			this.paddleSpeed = paddleSpeed;
			this.map = new int[GlobalVars.gameRows][GlobalVars.gameCols];
			this.mapJSON = map;
			this.breakBricks = 0;

			reloadMap();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public String getNext_uuid() {
		return next_uuid;
	}

	public String getName() {
		return name;
	}

	public long getBallSpeed() {
		return ballSpeed;
	}

	public long getPaddleSpeed() {
		return paddleSpeed;
	}

	public int[][] getMap() {
		return map;
	}

	public void removeBrick(int i, int j) {
		breakBricks -= map[i][j];
	}

	public long getBreakBricks() {
		return breakBricks;
	}

	public long getBestTime() {
		return bestTime;
	}

	public void setBestTime(long bestTime) {
		this.bestTime = bestTime;

		saveToJSON();
	}

	public void reloadMap() {
		/* ##############################
		 * Generate map from JSON Array
		 * ############################## */
		int pos = 0;
		this.breakBricks = 0;

		for (int i = 0; i < GlobalVars.gameRows; i++) {
			for (int j = 0; j < GlobalVars.gameCols; j++) {
				int block = Integer.parseInt(mapJSON.get(pos).toString());
				pos++;

				this.breakBricks += block;
				this.map[i][j] = block;
			}
		}
	}

	private void saveToJSON() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("uuid", uuid);
		jsonObj.put("name", name);
		jsonObj.put("map", mapJSON);
		jsonObj.put("next_uuid", next_uuid);
		jsonObj.put("ballSpeed", ballSpeed);
		jsonObj.put("bestTime", bestTime);

		try (FileWriter file = new FileWriter(GlobalVars.dirBase + "levels/" + uuid + ".json")) {
			file.write(jsonObj.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return name + " | Tempo migliore: " + GlobalVars.timeParser(bestTime);
	}
}
