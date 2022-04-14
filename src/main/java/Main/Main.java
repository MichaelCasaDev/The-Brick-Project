package Main;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import Game_Test.Gameplay;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;


public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        JFrame frame = new JFrame();
        Gameplay gamePlay = new Gameplay();

        frame.setSize(1920, 1080);

        frame.setTitle("The Brick");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(gamePlay);

        URL resource = Main.class.getClassLoader().getResource("icon.png");
        frame.setIconImage(new ImageIcon(resource).getImage());

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

        File folder = new File("/Users/michaelcasa/Desktop/School/TheBrickProject_IntelliJ/src/main/resources/levels");  // <- need to search how to make this dyanmic like the imageicon above...
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println("File " + listOfFiles[i].getName());
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
    }
}