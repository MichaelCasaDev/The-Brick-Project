package Control;

import View.*;

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
        impostazioniPanel.getBtnTornaIndietro().addActionListener(e -> tornaIndietroBtnHandler());

        // giocaPanel
        giocaPanel.getBtnTornaIndietro().addActionListener(e -> tornaIndietroBtnHandler());

        // informazioniPanel
        informazioniPanel.getBtnTornaIndietro().addActionListener(e -> tornaIndietroBtnHandler());

        // comeSiGiocaPanel
        comeSiGiocaPanel.getBtnTornaIndietro().addActionListener(e -> tornaIndietroBtnHandler());
    }

    private void tornaIndietroBtnHandler() {
        window.setContentPane(mainMenuPanel);
        window.revalidate();
    }

    private void changePanel(JPanel newPanel) {
        window.setContentPane(newPanel);
        window.revalidate();
    }
}
