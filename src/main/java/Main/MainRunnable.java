package Main;

import Control.Controller;
import View.*;
import View.EndGameScreen;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;


public class MainRunnable {
    public static void main(String[] args){
        /* ##############################
         * Panels
         * ############################## */
        MainMenuPanel mainMenuPanel = new MainMenuPanel();
        ImpostazioniPanel impostazioniPanel = new ImpostazioniPanel();
        GiocaPanel giocaPanel = new GiocaPanel();
        InformazioniPanel informazioniPanel = new InformazioniPanel();
        ComeSiGiocaPanel comeSiGiocaPanel = new ComeSiGiocaPanel();
        EndGameScreen endGameScreen = new EndGameScreen();

        /* ##############################
         * Main Logic
         * ############################## */
        Window window = new Window(new WindowAdapter() { // Window for everything with custom close method
            @Override
            public void windowClosing(WindowEvent e) {
                int exitConfirm = JOptionPane.showConfirmDialog(null, "Sei sicuro di voler uscire? Ogni progresso non salvato verrà perso!", "Esci", JOptionPane.CLOSED_OPTION);

                if(exitConfirm == 0) {
                    System.exit(0);
                }
            }
        });

        filesManager(); // Generate useful files if not present (levels, users, sounds, global)

        window.setPane(mainMenuPanel); // Default contentPane
        new Controller(window, mainMenuPanel, impostazioniPanel, giocaPanel, informazioniPanel, comeSiGiocaPanel, endGameScreen);
    }

    private static void filesManager() {
        if(!new File(GlobalVars.dirBase).exists()) {
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

                // Global
                final String dirGlobal = MainRunnable.class.getClassLoader().getResource("").getPath();
                Files.copy(new File(dirGlobal + "global.json").toPath(), new File(GlobalVars.dirBase + "/global.json").toPath(), StandardCopyOption.REPLACE_EXISTING);

                System.out.println("Files generated!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}