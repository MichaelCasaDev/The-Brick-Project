package Main;

import Control.Controller;
import Model.GlobalManager;
import Model.LevelManager;
import Model.UserManager;
import View.*;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * The main
 */
public class MainRunnable {
    /**
     * The main
     * @param args no args are used in the program
     */
    public static void main(String[] args){
        GlobalVars.filesManager(false); // Generate useful files if not present (levels, users, sounds, global files, etc...)

        /* ---------------------------------------------------------------------------------------------------------------- */
        // Panels
        /* ---------------------------------------------------------------------------------------------------------------- */
        MainMenuPanel mainMenuPanel = new MainMenuPanel();
        ImpostazioniPanel impostazioniPanel = new ImpostazioniPanel();
        GiocaPanel giocaPanel = new GiocaPanel();
        InformazioniPanel informazioniPanel = new InformazioniPanel();
        ComeSiGiocaPanel comeSiGiocaPanel = new ComeSiGiocaPanel();
        EndGameScreen endGameScreen = new EndGameScreen();

        /* ---------------------------------------------------------------------------------------------------------------- */
        // Managers
        /* ---------------------------------------------------------------------------------------------------------------- */
        UserManager userManager = new UserManager();
        LevelManager levelManager = new LevelManager();
        GlobalManager globalManager = new GlobalManager(GlobalVars.dirBase + "global.json");

        /* ---------------------------------------------------------------------------------------------------------------- */
        // Main Logic
        /* ---------------------------------------------------------------------------------------------------------------- */
        Window window = new Window(new WindowAdapter() { // Window for everything with custom close method
            @Override
            public void windowClosing(WindowEvent e) {
                int exitConfirm = JOptionPane.showConfirmDialog(null, "Sei sicuro di voler uscire? Ogni progresso non salvato verrà perso!", "Esci", JOptionPane.PLAIN_MESSAGE);

                if(exitConfirm == 0) {
                    System.exit(0);
                }
            }
        });

        window.setPane(mainMenuPanel); // Default contentPane
        new Controller(
                window, // Frame
                mainMenuPanel, impostazioniPanel, giocaPanel, informazioniPanel, comeSiGiocaPanel, endGameScreen, // Panels
                userManager, levelManager, globalManager // Managers
        );
    }
}