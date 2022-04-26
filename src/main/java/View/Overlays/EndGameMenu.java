package View.Overlays;

import Main.GlobalVars;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class EndGameMenu extends JPanel {
	private JLabel lblWin;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JSeparator separator;
	private JLabel lblBestTimeLevel;
	private JLabel lblUserTime;
	private JButton btnBackGiocaPanel;
	private JButton btnEsci;
	private JPanel panel;

	/**
	 * Create the panel.
	 */
	public EndGameMenu() {
		setLayout(null);
		setSize(GlobalVars.frameWidth, GlobalVars.frameHeight);

		lblWin = new JLabel("Hai vinto!");
		lblWin.setForeground(Color.GREEN);
		lblWin.setFont(new Font("Lucida Grande", Font.PLAIN, 32));
		lblWin.setBounds(608, 128, 149, 38);
		add(lblWin);
		
		panel = new JPanel();
		panel.setBorder(null);
		panel.setBounds(560, 256, 246, 44);
		add(panel);
		panel.setLayout(null);
		
		lblNewLabel_1 = new JLabel("Tempo migliore livello:");
		lblNewLabel_1.setBounds(0, 0, 149, 16);
		panel.add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("Il tuo tempo:");
		lblNewLabel_2.setBounds(0, 28, 149, 16);
		panel.add(lblNewLabel_2);
		
		separator = new JSeparator();
		separator.setBounds(161, 0, 12, 44);
		panel.add(separator);
		separator.setOrientation(SwingConstants.VERTICAL);
		
		lblBestTimeLevel = new JLabel("-");
		lblBestTimeLevel.setBounds(185, 0, 61, 16);
		panel.add(lblBestTimeLevel);
		
		lblUserTime = new JLabel("-");
		lblUserTime.setBounds(185, 28, 61, 16);
		panel.add(lblUserTime);
		
		btnBackGiocaPanel = new JButton("Torna alla selezione dei livelli");
		btnBackGiocaPanel.setBounds(568, 360, 230, 29);
		add(btnBackGiocaPanel);
		
		btnEsci = new JButton("Esci");
		btnEsci.setForeground(Color.RED);
		btnEsci.setBounds(624, 400, 117, 29);
		add(btnEsci);

	}

	public JLabel getLblWin() {
		return lblWin;
	}

	public JLabel getLblBestTimeLevel() {
		return lblBestTimeLevel;
	}

	public JLabel getLblUserTime() {
		return lblUserTime;
	}

	public JButton getBtnBackGiocaPanel() {
		return btnBackGiocaPanel;
	}

	public JButton getBtnEsci() {
		return btnEsci;
	}
}
