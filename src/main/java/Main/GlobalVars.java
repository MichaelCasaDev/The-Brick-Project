package Main;

import javax.swing.*;
import java.awt.Color;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.TimeUnit;

public class GlobalVars {
    public static final String dirBase = System.getProperty("user.dir") + "/theBrickData/";

    /* ---------------------------------------------------------------------------------------------------------------- */

    public static final int frameWidth = 1366;
    public static final int frameHeight = 768;

    public static final int playAreaWidth = 1238;
    public static final int playAreaHeight = 256;

    public static final int brickWidth = GlobalVars.playAreaWidth / GlobalVars.gameCols;
    public static final int brickHeight = GlobalVars.playAreaHeight / GlobalVars.gameRows;

    /* ---------------------------------------------------------------------------------------------------------------- */

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

    /* ---------------------------------------------------------------------------------------------------------------- */

    public static String timeParser(long time) {
        long timeSeconds = TimeUnit.SECONDS.convert(time, TimeUnit.SECONDS);
        long timeMinutes = TimeUnit.MINUTES.convert(time, TimeUnit.SECONDS);
        long realTimeSeconds = (timeSeconds >= 60 ? (timeSeconds - (timeMinutes * 60)) : timeSeconds);

        return (timeMinutes >= 10 ? timeMinutes : ("0" + timeMinutes)) + ":" + (realTimeSeconds >= 10 ? "" : "0") + realTimeSeconds;
    }

    public static void filesManager(boolean forced) {
        if(!new File(GlobalVars.dirBase).exists() || forced) {
            System.out.println("Need to create 'The Brick' data folders and files");

            try {
                new File(GlobalVars.dirBase).mkdir();
                System.out.println("Folder created!");
                System.out.println("Start generating files...");

                // Levels
                new File(GlobalVars.dirBase + "levels").mkdir();
                final String dirLevels = MainRunnable.class.getClassLoader().getResource("levels").getPath();
                File folderLevels = new File(dirLevels);
                for(File file : folderLevels.listFiles()) {
                    Files.copy(file.toPath(), new File(GlobalVars.dirBase + "levels/" + file.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
                }

                // Sounds
                new File(GlobalVars.dirBase + "sounds").mkdir();
                final String dirSounds = MainRunnable.class.getClassLoader().getResource("sounds").getPath();
                File folderSounds = new File(dirSounds);
                for(File file : folderSounds.listFiles()) {
                    Files.copy(file.toPath(), new File(GlobalVars.dirBase + "sounds/" + file.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
                }

                // Users
                new File(GlobalVars.dirBase + "users").mkdir();
                final String dirUsers = MainRunnable.class.getClassLoader().getResource("users").getPath();
                File folderUsers = new File(dirUsers);
                for(File file : folderUsers.listFiles()) {
                    Files.copy(file.toPath(), new File(GlobalVars.dirBase + "users/" + file.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
                }

                // Images
                new File(GlobalVars.dirBase + "images").mkdir();
                final String dirImages = MainRunnable.class.getClassLoader().getResource("images").getPath();
                File folderImages = new File(dirImages);
                for(File file : folderImages.listFiles()) {
                    Files.copy(file.toPath(), new File(GlobalVars.dirBase + "images/" + file.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
                }

                // Global
                final String dirGlobal = MainRunnable.class.getClassLoader().getResource("").getPath();
                Files.copy(new File(dirGlobal + "global.json").toPath(), new File(GlobalVars.dirBase + "/global.json").toPath(), StandardCopyOption.REPLACE_EXISTING);

                System.out.println("Files generated!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Errore - " + e.getMessage(), "Exception", JOptionPane.PLAIN_MESSAGE);
            }
        } else {
            System.out.println("All files exists! Running...");
        }
    }
}
