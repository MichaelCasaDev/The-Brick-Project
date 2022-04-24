package Main;

import Control.PanelController;
import View.*;

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

        // Generate useful files if not present (levels, users, sounds, global)
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
                for(File fileLevel : folderLevels.listFiles()) {
                    Files.copy(fileLevel.toPath(), new File(GlobalVars.dirBase + "levels/" + fileLevel.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
                }

                // Sounds
                new File(GlobalVars.dirBase + "sounds").mkdir();
                final String dirSounds = MainRunnable.class.getClassLoader().getResource("sounds").getPath();
                File folderSounds = new File(dirSounds);
                for(File fileLevel : folderSounds.listFiles()) {
                    Files.copy(fileLevel.toPath(), new File(GlobalVars.dirBase + "sounds/" + fileLevel.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
                }

                // Users
                new File(GlobalVars.dirBase + "users").mkdir();
                final String dirUsers = MainRunnable.class.getClassLoader().getResource("users").getPath();
                File folderUsers = new File(dirUsers);
                for(File fileLevel : folderUsers.listFiles()) {
                    Files.copy(fileLevel.toPath(), new File(GlobalVars.dirBase + "users/" + fileLevel.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
                }

                // Global
                final String dirGlobal = MainRunnable.class.getClassLoader().getResource("").getPath();
                Files.copy(new File(dirGlobal + "global.json").toPath(), new File(GlobalVars.dirBase + "/global.json").toPath(), StandardCopyOption.REPLACE_EXISTING);

                System.out.println("Files generated!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        window.setContentPane(mainMenuPanel); // Default contentPane
        new PanelController(window, mainMenuPanel, impostazioniPanel, giocaPanel, informazioniPanel, comeSiGiocaPanel);
    }
}