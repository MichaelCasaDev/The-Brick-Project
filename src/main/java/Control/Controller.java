package Control;

import Main.GameplayEvents;
import Main.GlobalVars;
import Model.*;
import View.*;
import View.EndGameScreen;
import View.Window;

import javax.swing.*;
import java.awt.*;

public class Controller {
    private Window window;

    private MainMenuPanel mainMenuPanel;
    private ImpostazioniPanel impostazioniPanel;
    private GiocaPanel giocaPanel;
    private InformazioniPanel informazioniPanel;
    private ComeSiGiocaPanel comeSiGiocaPanel;
    private EndGameScreen endGameScreen;

    private UserManager userManager;
    private LevelManager levelManager;
    private GlobalManager globalManager;

    private Level selectedLevel;

    public Controller(Window window, MainMenuPanel mainMenuPanel, ImpostazioniPanel impostazioniPanel, GiocaPanel giocaPanel, InformazioniPanel informazioniPanel, ComeSiGiocaPanel comeSiGiocaPanel, EndGameScreen endGameScreen) {
        this.window = window;

        this.mainMenuPanel = mainMenuPanel;
        this.impostazioniPanel = impostazioniPanel;
        this.giocaPanel = giocaPanel;
        this.informazioniPanel = informazioniPanel;
        this.comeSiGiocaPanel = comeSiGiocaPanel;
        this.endGameScreen = endGameScreen;

        this.userManager = new UserManager();
        this.levelManager = new LevelManager();
        this.globalManager = new GlobalManager(GlobalVars.dirBase + "global.json");

        this.selectedLevel = null;

        listeners();
    }


    /* -------------------------------------------------------------------------------------------------------- */
    // Listeners
    /* -------------------------------------------------------------------------------------------------------- */
    private void listeners() {
        /* -------------------------------------------------------------------------------------------------------- */
        // mainMenuPanel
        /* -------------------------------------------------------------------------------------------------------- */
        mainMenuPanel.getBtnImpostazioni().addActionListener(e -> {
            changePanel(impostazioniPanel);

            reloadUserData(true);
        });
        mainMenuPanel.getBtnGioca().addActionListener(e -> changePanel(giocaPanel));
        mainMenuPanel.getBtnInformazioni().addActionListener(e -> changePanel(informazioniPanel));
        mainMenuPanel.getBtnComeSiGioca().addActionListener(e -> changePanel(comeSiGiocaPanel));
        mainMenuPanel.getBtnEsci().addActionListener(e -> exitSafe());
        mainMenuPanel.getLblUsername().setText("Benvenuto: " + userManager.get(globalManager.getLastUser()).toString());

        /* -------------------------------------------------------------------------------------------------------- */
        // impostazioniPanel
        /* -------------------------------------------------------------------------------------------------------- */
        impostazioniPanel.getBtnTornaIndietro().addActionListener(e -> backToMainMenu());
        impostazioniPanel.getBtnSalva().addActionListener((e) -> {
            userManager.get(globalManager.getLastUser()).setUsername(impostazioniPanel.getTextFieldUsername().getText());
            userManager.get(globalManager.getLastUser()).setSounds(impostazioniPanel.getChckbxSuoni().isSelected());

            // Reload users
            impostazioniPanel.getComboBoxUtenti().removeAllItems();
            for(User u : userManager.getList()) {
                impostazioniPanel.getComboBoxUtenti().addItem(u);
            }

            // Reload user username
            mainMenuPanel.getLblUsername().setText("Benvenuto: " + userManager.get(globalManager.getLastUser()).toString());

            // Say saved to the user
            JOptionPane.showMessageDialog(null, "Dati aggiornati con successo!", "Salva impostazioni", JOptionPane.INFORMATION_MESSAGE);
        });
        impostazioniPanel.getBtnReset().addActionListener((e) -> {
            userManager.get(globalManager.getLastUser()).setLevel(globalManager.getLevel0());
            userManager.get(globalManager.getLastUser()).setTotBricksBreak(0);
            userManager.get(globalManager.getLastUser()).setTotPlayGame(0);

            reloadUserData(false);

            // Say reset to the user
            JOptionPane.showMessageDialog(null, "Statistiche reimpostate con successo!", "Reset statistiche", JOptionPane.INFORMATION_MESSAGE);
        });
        impostazioniPanel.getComboBoxUtenti().addActionListener(e -> {
            // Update displayed information
            if(impostazioniPanel.getComboBoxUtenti().getSelectedItem() != null){
                globalManager.setLastUser((User) impostazioniPanel.getComboBoxUtenti().getSelectedItem());

                reloadUserData(false);
            }
        });
        impostazioniPanel.getBtnAggiungi().addActionListener(e -> {
            // Create new plain user
            userManager.add(new User());

            reloadUserData(true);
        });
        impostazioniPanel.getBtnRimuovi().addActionListener(e -> {
            // Update displayed information
            if(impostazioniPanel.getComboBoxUtenti().getSelectedItem() != null){
                userManager.remove((User) impostazioniPanel.getComboBoxUtenti().getSelectedItem());

                reloadUserData(true);
            }
        });

        /* -------------------------------------------------------------------------------------------------------- */
        // giocaPanel
        /* -------------------------------------------------------------------------------------------------------- */
        giocaPanel.getBtnTornaIndietro().addActionListener(e -> backToMainMenu());
        giocaPanel.getList().setModel(levelManager.getListModel());
        giocaPanel.getList().addListSelectionListener(e -> giocaPanel.getBtnGioca().setEnabled(true));

        giocaPanel.getBtnGioca().addActionListener(e -> {
            selectedLevel = (Level) giocaPanel.getList().getSelectedValue();
            GamePlay gamePlay = new GamePlay(selectedLevel, userManager.get(globalManager.getLastUser()));

            runGamePlay(gamePlay);
        });

        giocaPanel.getBtnStoria().addActionListener(e -> {
            selectedLevel = levelManager.parser(userManager.get(globalManager.getLastUser()).getLevel());
            GamePlay gamePlay = new GamePlay(selectedLevel, userManager.get(globalManager.getLastUser()));

            runGamePlay(gamePlay);
        });

        /* -------------------------------------------------------------------------------------------------------- */
        // endGameScreen
        /* -------------------------------------------------------------------------------------------------------- */
        endGameScreen.getBtnEsci().addActionListener(ev -> exitSafe());
        endGameScreen.getBtnBackGiocaPanel().addActionListener(ev -> changePanel(giocaPanel));
        endGameScreen.getBtnPlayAgain().addActionListener(ev -> {
            GamePlay gamePlay = new GamePlay(selectedLevel, userManager.get(globalManager.getLastUser()));

            runGamePlay(gamePlay);
        });

        /* -------------------------------------------------------------------------------------------------------- */
        // informazioniPanel
        /* -------------------------------------------------------------------------------------------------------- */
        informazioniPanel.getBtnTornaIndietro().addActionListener(e -> backToMainMenu());

        /* -------------------------------------------------------------------------------------------------------- */
        // comeSiGiocaPanel
        /* -------------------------------------------------------------------------------------------------------- */
        comeSiGiocaPanel.getBtnTornaIndietro().addActionListener(e -> backToMainMenu());
    }

    /* -------------------------------------------------------------------------------------------------------- */
    // Private methods
    /* -------------------------------------------------------------------------------------------------------- */
    private void backToMainMenu() {
        window.setPane(mainMenuPanel);
        giocaPanel.getBtnGioca().setEnabled(false);
    }

    private void changePanel(JPanel newPanel) {
        window.setPane(newPanel);
    }

    private void exitSafe() {
        int exitConfirm = JOptionPane.showConfirmDialog(null, "Sei sicuro di voler uscire? Ogni progresso non salvato verrà perso!", "Esci", JOptionPane.CLOSED_OPTION);

        if(exitConfirm == 0) {
            System.exit(0);
        }
    }

    private void reloadUserData(boolean full) {
        if(full) {
            impostazioniPanel.getComboBoxUtenti().removeAllItems();
            for(User u : userManager.getList()) {
                impostazioniPanel.getComboBoxUtenti().addItem(u);
            }
        }

        User user = userManager.get(globalManager.getLastUser());

        mainMenuPanel.getLblUsername().setText("Benvenuto: " + user.toString());

        impostazioniPanel.getTextFieldUsername().setText(user.getUsername());
        impostazioniPanel.getChckbxSuoni().setSelected(user.getSounds());
        impostazioniPanel.getLblLevel().setText(levelManager.parser(user.getLevel()).getName());
        impostazioniPanel.getLblTotBricksBreak().setText("" + user.getTotBricksBreak());
        impostazioniPanel.getLblTotPlayGame().setText("" + user.getTotPlayGame());
    }

    private void runGamePlay(GamePlay gamePlay) {
        // Reset some things
        giocaPanel.getList().clearSelection();
        giocaPanel.getBtnGioca().setEnabled(false);

        // Custom gameplay listeners for endGame screen
        gamePlay.addGameplayListener(new GameplayEvents() {
            @Override
            public void endMenuOpen(boolean win) {
                if(win) {
                    endGameScreen.getLblWin().setText("Hai vinto!");
                    endGameScreen.getLblWin().setForeground(Color.GREEN);

                    // Check level best time and edit if necessary
                    if(selectedLevel.getBestTime() == 0 || gamePlay.getTime() < selectedLevel.getBestTime()) {
                        selectedLevel.setBestTime(gamePlay.getTime());
                    }

                    userManager.get(globalManager.getLastUser()).setLevel(selectedLevel.getNext_uuid());

                    if(userManager.get(globalManager.getLastUser()).getLevel().equals("-1")) {
                        giocaPanel.getBtnStoria().setEnabled(false);
                    }
                } else {
                    endGameScreen.getLblWin().setText("Hai perso!");
                    endGameScreen.getLblWin().setForeground(Color.RED);
                }

                endGameScreen.getLblBestTimeLevel().setText(GlobalVars.timeParser(selectedLevel.getBestTime()));
                endGameScreen.getLblUserTime().setText(gamePlay.getTimeStr());

                changePanel(endGameScreen);
            }

            @Override
            public void inGameMenuClose() {
                changePanel(mainMenuPanel);
            }
        });

        changePanel(gamePlay);
    }
}