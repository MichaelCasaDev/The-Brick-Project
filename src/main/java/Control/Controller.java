package Control;

import Main.GlobalVars;
import Model.*;
import View.*;
import View.EndGameScreen;
import View.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private Window window;

    private MainMenuPanel mainMenuPanel;
    private ImpostazioniPanel impostazioniPanel;
    private GiocaPanel giocaPanel;
    private InformazioniPanel informazioniPanel;
    private ComeSiGiocaPanel comeSiGiocaPanel;
    private EndGameScreen endGameScreen;

    public Controller(Window window, MainMenuPanel mainMenuPanel, ImpostazioniPanel impostazioniPanel, GiocaPanel giocaPanel, InformazioniPanel informazioniPanel, ComeSiGiocaPanel comeSiGiocaPanel, EndGameScreen endGameScreen) {
        this.window = window;

        this.mainMenuPanel = mainMenuPanel;
        this.impostazioniPanel = impostazioniPanel;
        this.giocaPanel = giocaPanel;
        this.informazioniPanel = informazioniPanel;
        this.comeSiGiocaPanel = comeSiGiocaPanel;
        this.endGameScreen = endGameScreen;

        listeners();
    }

    private void listeners() {
        UserManager y = new UserManager();
        LevelManager x = new LevelManager();
        GlobalManager z = new GlobalManager(GlobalVars.dirBase + "global.json");

        // mainMenuPanel
        mainMenuPanel.getBtnImpostazioni().addActionListener(e -> {
            changePanel(impostazioniPanel);

            impostazioniPanel.getLblLevel().setText(y.get(z.getLastUser()).getLevel());
            impostazioniPanel.getLblTotBricksBreak().setText("" + y.get(z.getLastUser()).getTotBricksBreak());
            impostazioniPanel.getLblTotPlayGame().setText("" + y.get(z.getLastUser()).getTotPlayGame());
        });
        mainMenuPanel.getBtnGioca().addActionListener(e -> changePanel(giocaPanel));
        mainMenuPanel.getBtnInformazioni().addActionListener(e -> changePanel(informazioniPanel));
        mainMenuPanel.getBtnComeSiGioca().addActionListener(e -> changePanel(comeSiGiocaPanel));
        mainMenuPanel.getBtnEsci().addActionListener(e -> exitSafe());
        mainMenuPanel.getLblUsername().setText("Benvenuto: " + y.get(z.getLastUser()).toString());

        // impostazioniPanel
        for(User u : y.getList()) {
            impostazioniPanel.getComboBoxUtenti().addItem(u);
        }

        impostazioniPanel.getBtnTornaIndietro().addActionListener(e -> backToMainMenu());
        impostazioniPanel.getTextFieldUsername().setText(y.get(z.getLastUser()).getUsername());
        impostazioniPanel.getChckbxSuoni().setSelected(y.get(z.getLastUser()).getSounds());
        impostazioniPanel.getBtnSalva().addActionListener((e) -> {
            y.get(z.getLastUser()).setUsername(impostazioniPanel.getTextFieldUsername().getText());
            y.get(z.getLastUser()).setSounds(impostazioniPanel.getChckbxSuoni().isSelected());

            // Reload users
            for(User u : y.getList()) {
                impostazioniPanel.getComboBoxUtenti().addItem(u);
            }

            // Reload user username
            mainMenuPanel.getLblUsername().setText("Benvenuto: " + y.get(z.getLastUser()).toString());
        });
        impostazioniPanel.getBtnReset().addActionListener((e) -> {
            y.get(z.getLastUser()).setLevel("0");
            y.get(z.getLastUser()).setTotBricksBreak(0);
            y.get(z.getLastUser()).setTotPlayGame(0);

            impostazioniPanel.getLblLevel().setText(y.get(z.getLastUser()).getLevel());
            impostazioniPanel.getLblTotBricksBreak().setText("" + y.get(z.getLastUser()).getTotBricksBreak());
            impostazioniPanel.getLblTotPlayGame().setText("" + y.get(z.getLastUser()).getTotPlayGame());
        });
        impostazioniPanel.getComboBoxUtenti().addActionListener(e -> {
            z.setLastUser((User) impostazioniPanel.getComboBoxUtenti().getSelectedItem());
        });

        // giocaPanel
        giocaPanel.getBtnTornaIndietro().addActionListener(e -> backToMainMenu());
        giocaPanel.getList().setModel(x.getListModel());
        giocaPanel.getList().addListSelectionListener(e -> giocaPanel.getBtnGioca().setEnabled(true));

        giocaPanel.getBtnGioca().addActionListener(e -> {
            Level selectedLevel = (Level) giocaPanel.getList().getSelectedValue();
            GamePlay gamePlay = new GamePlay(selectedLevel);

            // Reset some things
            giocaPanel.getList().clearSelection();
            giocaPanel.getBtnGioca().setSelected(false);

            // Custom gameplay listeners for endGame screen
            gamePlay.addGameplayListener(win -> {
                if(win) {
                    endGameScreen.getLblWin().setText("Hai vinto!");
                    endGameScreen.getLblWin().setForeground(Color.GREEN);
                } else {
                    endGameScreen.getLblWin().setText("Hai perso!");
                    endGameScreen.getLblWin().setForeground(Color.RED);
                }

                endGameScreen.getLblBestTimeLevel().setText("--:--");
                endGameScreen.getLblUserTime().setText(gamePlay.getTimeStr());

                changePanel(endGameScreen);
            });

            changePanel(gamePlay);
        });

        endGameScreen.getBtnEsci().addActionListener(ev -> exitSafe());
        endGameScreen.getBtnBackGiocaPanel().addActionListener(ev -> changePanel(giocaPanel));

        // informazioniPanel
        informazioniPanel.getBtnTornaIndietro().addActionListener(e -> backToMainMenu());

        // comeSiGiocaPanel
        comeSiGiocaPanel.getBtnTornaIndietro().addActionListener(e -> backToMainMenu());
    }

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
}
