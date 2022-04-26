package View.Overlays;

import Main.GlobalVars;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;

public class InGameMenu extends JPanel {
	private JLabel lblNewLabel;
	private JButton btnBackGiocaPanel;
	private JButton btnEsci;
	private JButton btnRiprendi;

	/**
	 * Create the panel.
	 */
	public InGameMenu() {
		setLayout(null);
		setSize(GlobalVars.frameWidth, GlobalVars.frameHeight);

		lblNewLabel = new JLabel("Pausa");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		lblNewLabel.setBounds(650, 64, 66, 30);
		add(lblNewLabel);
		
		btnBackGiocaPanel = new JButton("Torna alla selezione dei livelli");
		btnBackGiocaPanel.setBounds(568, 400, 230, 29);
		add(btnBackGiocaPanel);
		
		btnEsci = new JButton("Esci");
		btnEsci.setForeground(Color.RED);
		btnEsci.setBounds(624, 432, 117, 29);
		add(btnEsci);
		
		btnRiprendi = new JButton("Riprendi");
		btnRiprendi.setBounds(624, 256, 117, 29);
		add(btnRiprendi);

	}

	public JButton getBtnBackGiocaPanel() {
		return btnBackGiocaPanel;
	}

	public JButton getBtnEsci() {
		return btnEsci;
	}

	public JButton getBtnRiprendi() {
		return btnRiprendi;
	}
}
