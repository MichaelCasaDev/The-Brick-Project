package Main;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class GlobalVars {
    public static final String dirBase = System.getProperty("user.dir") + "/theBrickData/";

    public static final int frameWidth = 1366;
    public static final int frameHeight = 768;

    public static final int playAreaWidth = 1238;
    public static final int playAreaHeight = 256;

    public static final int brickWidth = GlobalVars.playAreaWidth / GlobalVars.gameCols;
    public static final int brickHeight = GlobalVars.playAreaHeight / GlobalVars.gameRows;

    public static final int gameRows = 6;
    public static final int gameCols = 20;
    public static final Color textColor = new Color(255, 255, 255);
    public static final Color backgroundColor = new Color(21, 21, 21);
    public static final Color backgroundColorAlt = new Color(25, 25, 25);
    public static final Color backgroundColorTransparent = new Color(0,0,0, 128);
    public static final Color brickColor = new Color(255, 101, 69);
    public static final Color extraBrickColor = new Color(255, 202, 69);
    public static final Color borderColor = new Color(255, 255, 255);
    public static final Color paddleColor = new Color(255, 255, 255);
    public static final Color ballColor = new Color(255, 255, 255);
    public static final Color scoreColor = new Color(255, 255, 255);
    public static final Color timerColor = new Color(255, 255, 255);

    public static String timeParser(long time) {
        long timeSeconds = TimeUnit.SECONDS.convert(time, TimeUnit.SECONDS);
        long timeMinutes = TimeUnit.MINUTES.convert(time, TimeUnit.SECONDS);
        long realTimeSeconds = (timeSeconds >= 60 ? (timeSeconds - (timeMinutes * 60)) : timeSeconds);

        return (timeMinutes >= 10 ? timeMinutes : ("0" + timeMinutes)) + ":" + (realTimeSeconds >= 10 ? "" : "0") + realTimeSeconds;
    }
}
