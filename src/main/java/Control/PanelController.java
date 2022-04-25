package Control;

import Model.Level;
import Model.LevelManager;
import View.*;
import View.GamePlay;

import javax.swing.*;

public class PanelController {
    private Window window;

    private MainMenuPanel mainMenuPanel;
    private ImpostazioniPanel impostazioniPanel;
    private GiocaPanel giocaPanel;
    private InformazioniPanel informazioniPanel;
    private ComeSiGiocaPanel comeSiGiocaPanel;

    public PanelController(Window window, MainMenuPanel mainMenuPanel, ImpostazioniPanel impostazioniPanel, GiocaPanel giocaPanel, InformazioniPanel informazioniPanel, ComeSiGiocaPanel comeSiGiocaPanel) {
        this.window = window;

        this.mainMenuPanel = mainMenuPanel;
        this.impostazioniPanel = impostazioniPanel;
        this.giocaPanel = giocaPanel;
        this.informazioniPanel = informazioniPanel;
        this.comeSiGiocaPanel = comeSiGiocaPanel;

        listeners();
    }

    private void listeners() {
        // mainMenuPanel
        mainMenuPanel.getBtnImpostazioni().addActionListener(e -> changePanel(impostazioniPanel));
        mainMenuPanel.getBtnGioca().addActionListener(e -> changePanel(giocaPanel));
        mainMenuPanel.getBtnInformazioni().addActionListener(e -> changePanel(informazioniPanel));
        mainMenuPanel.getBtnComeSiGioca().addActionListener(e -> changePanel(comeSiGiocaPanel));
        mainMenuPanel.getBtnEsci().addActionListener(e -> {
            int exitConfirm = JOptionPane.showConfirmDialog(null, "Sei sicuro di voler uscire? Ogni progresso non salvato verrà perso!", "Esci", JOptionPane.CLOSED_OPTION);

            if(exitConfirm == 0) {
                System.exit(0);
            }
        });

        // impostazioniPanel
        impostazioniPanel.getBtnTornaIndietro().addActionListener(e -> backToMainMenu());

        // giocaPanel
        LevelManager x = new LevelManager();
        giocaPanel.getBtnTornaIndietro().addActionListener(e -> backToMainMenu());
        giocaPanel.getList().setModel(x.getListModel());

        giocaPanel.getList().addListSelectionListener(e -> {
            giocaPanel.getBtnGioca().setEnabled(true);
        });

        giocaPanel.getBtnGioca().addActionListener(e -> {
            Level selectedLevel = (Level) giocaPanel.getList().getSelectedValue();
            GamePlay gamePlay = new GamePlay(selectedLevel);

            /*
            gamePlay.addMenuListener(e -> {

            });
            */

            window.setPane(gamePlay);
            window.revalidate();
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
}
