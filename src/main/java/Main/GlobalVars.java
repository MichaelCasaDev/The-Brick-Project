package Main;

import java.awt.*;

public class GlobalVars {
    public static final String dirBase = System.getProperty("user.dir") + "/theBrickData/";

    public static final int frameWidth = 1366;
    public static final int frameHeight = 768;

    public static final int playAreaWidth = 1238;
    public static final int playAreaHeight = 256;

    public static final int brickWidth = GlobalVars.playAreaWidth / GlobalVars.gameCols;
    public static final int brickHeight = GlobalVars.playAreaHeight / GlobalVars.gameRows;

    public static final int gameRows = 2;
    public static final int gameCols = 15;
    public static final Color backgroundColor = new Color(0, 0, 0);
    public static final Color brickColor = new Color(255, 101, 69);
    public static final Color borderColor = new Color(255, 255, 255);
    public static final Color paddleColor = new Color(128, 255, 210);
    public static final Color ballColor = new Color(108, 110, 234);
}
