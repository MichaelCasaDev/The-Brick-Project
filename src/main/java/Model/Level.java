package Model;

import Main.GlobalVars;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class Level {
	private String uuid;
	private String next_uuid;
	private String name;
	private String path;
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
			String uuid = (String) jsonFile.get("uuid");
			String next_uuid = (String) jsonFile.get("next_uuid");
			long ballSpeed = (long) jsonFile.get("ballSpeed");
			long totBricks = (long) jsonFile.get("totBricks");

			/* ##############################
			 * Save data from parsed JSON File
			 * ############################## */
			this.uuid = uuid;
			this.next_uuid = next_uuid;
			this.name = name;
			this.path = path;
			this.totBricks = totBricks;
			this.breakBricks = totBricks;
			this.ballSpeed = ballSpeed;
			this.map = new int[GlobalVars.gameRows][GlobalVars.gameCols];
			this.mapJSON = map;

			reloadMap();
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

	public void removeBrick() {
		breakBricks--;
	}

	public long getBreakBricks() {
		return breakBricks;
	}

	public void reloadMap() {
		/* ##############################
		 * Generate map from JSON Array
		 * ############################## */
		int pos = 0;
		for (int i = 0; i < GlobalVars.gameRows; i++) {
			for (int j = 0; j < GlobalVars.gameCols; j++) {
				int block = Integer.parseInt(mapJSON.get(pos).toString());
				pos++;

				this.map[i][j] = block;
			}
		}
	}

	@Override
	public String toString() {
		return name;
	}
}
