package Game_Test;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.FileReader;
import java.io.IOException;

public class MapGenerator {
    public int map[][];
    public int brickWidth;
    public int brickHeight;

    public MapGenerator(int row, int col) throws IOException, ParseException {
        map = new int[row][col];

        JSONParser parser = new JSONParser();
        JSONObject a = (JSONObject) parser.parse(new FileReader("/Users/michaelcasa/Desktop/School/TheBrickProject_IntelliJ/src/main/resources/levels/0.json"));
        JSONObject b = (JSONObject) a.get("blocks");
        JSONArray c = (JSONArray) b.get("map");

        int nani = 0;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                System.out.println(c.get(nani).toString());
                int block = Integer.parseInt(c.get(nani).toString());
                nani++;

                map[i][j] = block;
            }
        }

        brickWidth = 1760 / col;
        brickHeight = 200 / row;
    }

    public void draw(Graphics2D g) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] != 0) { // Create the white box if isn't already broken
                    g.setColor(Color.WHITE);

                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);

                    // this is just to show separate brick, game can still run without it
                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.BLACK);
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                }
            }
        }
    }

    public void setBrickValue(int value, int row, int col) {
        map[row][col] = value;
    }
}
