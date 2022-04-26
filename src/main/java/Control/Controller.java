package Control;

import Main.GameplayEvents;
import Model.Level;
import Model.LevelManager;
import View.*;
import View.GamePlay;
import View.Overlays.EndGameMenu;
import View.Overlays.InGameMenu;
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

    private EndGameMenu endGameMenu;
    private InGameMenu inGameMenu;

    public Controller(Window window, MainMenuPanel mainMenuPanel, ImpostazioniPanel impostazioniPanel, GiocaPanel giocaPanel, InformazioniPanel informazioniPanel, ComeSiGiocaPanel comeSiGiocaPanel, EndGameMenu endGameMenu, InGameMenu inGameMenu) {
        this.window = window;

        this.mainMenuPanel = mainMenuPanel;
        this.impostazioniPanel = impostazioniPanel;
        this.giocaPanel = giocaPanel;
        this.informazioniPanel = informazioniPanel;
        this.comeSiGiocaPanel = comeSiGiocaPanel;

        this.endGameMenu = endGameMenu;
        this.inGameMenu = inGameMenu;

        listeners();
    }

    private void listeners() {
        // mainMenuPanel
        mainMenuPanel.getBtnImpostazioni().addActionListener(e -> changePanel(impostazioniPanel));
        mainMenuPanel.getBtnGioca().addActionListener(e -> changePanel(giocaPanel));
        mainMenuPanel.getBtnInformazioni().addActionListener(e -> changePanel(informazioniPanel));
        mainMenuPanel.getBtnComeSiGioca().addActionListener(e -> changePanel(comeSiGiocaPanel));
        mainMenuPanel.getBtnEsci().addActionListener(e -> exitSafe());

        // impostazioniPanel
        impostazioniPanel.getBtnTornaIndietro().addActionListener(e -> backToMainMenu());

        // giocaPanel
        LevelManager x = new LevelManager();
        giocaPanel.getBtnTornaIndietro().addActionListener(e -> backToMainMenu());
        giocaPanel.getList().setModel(x.getListModel());
        giocaPanel.getList().addListSelectionListener(e -> giocaPanel.getBtnGioca().setEnabled(true));

        giocaPanel.getBtnGioca().addActionListener(e -> {
            Level selectedLevel = (Level) giocaPanel.getList().getSelectedValue();
            GamePlay gamePlay = new GamePlay(selectedLevel);

            gamePlay.addGameplayListener(new GameplayEvents() {
                @Override
                public void inGameMenuOpen() {
                    changePanel(inGameMenu);
                }

                @Override
                public void endMenuOpen(boolean win) {
                    if(win) {
                        endGameMenu.getLblWin().setText("Hai vinto!");
                        endGameMenu.getLblWin().setForeground(Color.GREEN);
                    } else {
                        endGameMenu.getLblWin().setText("Hai perso!");
                        endGameMenu.getLblWin().setForeground(Color.RED);
                    }

                    endGameMenu.getLblBestTimeLevel().setText("--");
                    endGameMenu.getLblUserTime().setText("--");

                    changePanel(endGameMenu);
                }
            });

            changePanel(gamePlay);


            inGameMenu.getBtnEsci().addActionListener(ev -> exitSafe());
            inGameMenu.getBtnBackGiocaPanel().addActionListener(ev -> changePanel(giocaPanel));
            inGameMenu.getBtnRiprendi().addActionListener(ev -> {
                changePanel(gamePlay);
                gamePlay.riprendi();

            });

            endGameMenu.getBtnEsci().addActionListener(ev -> exitSafe());
            endGameMenu.getBtnBackGiocaPanel().addActionListener(ev -> changePanel(giocaPanel));
        });

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
