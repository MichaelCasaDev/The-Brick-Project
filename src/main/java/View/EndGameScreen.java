package View;

import Main.GlobalVars;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class EndGameScreen extends JPanel {
	private JLabel lblWin;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JSeparator separator;
	private JLabel lblBestTimeLevel;
	private JLabel lblUserTime;
	private JButton btnBackGiocaPanel;
	private JButton btnPlayAgain;
	private JButton btnEsci;
	private JPanel panel;

	/**
	 * Create the panel.
	 */
	public EndGameScreen() {
		setBackground(GlobalVars.backgroundColor);
		setLayout(null);
		setSize(GlobalVars.frameWidth, GlobalVars.frameHeight);

		lblWin = new JLabel("Hai vinto!");
		lblWin.setHorizontalAlignment(SwingConstants.CENTER);
		lblWin.setForeground(Color.GREEN);
		lblWin.setFont(new Font("Lucida Grande", Font.PLAIN, 32));
		lblWin.setBounds(560, 128, 246, 38);
		add(lblWin);
		
		panel = new JPanel();
		panel.setOpaque(true);
		panel.setBackground(GlobalVars.backgroundColor);
		panel.setBorder(null);
		panel.setBounds(560, 256, 246, 44);
		add(panel);
		panel.setLayout(null);
		
		lblNewLabel_1 = new JLabel("Tempo migliore globale");
		lblNewLabel_1.setForeground(GlobalVars.textColor);
		lblNewLabel_1.setBounds(0, 0, 149, 16);
		panel.add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("Il tuo tempo");
		lblNewLabel_2.setForeground(GlobalVars.textColor);
		lblNewLabel_2.setBounds(0, 28, 149, 16);
		panel.add(lblNewLabel_2);
		
		separator = new JSeparator();
		separator.setBounds(161, 0, 12, 44);
		panel.add(separator);
		separator.setOrientation(SwingConstants.VERTICAL);
		
		lblBestTimeLevel = new JLabel("-");
		lblBestTimeLevel.setForeground(GlobalVars.textColor);
		lblBestTimeLevel.setBounds(185, 0, 61, 16);
		panel.add(lblBestTimeLevel);
		
		lblUserTime = new JLabel("-");
		lblUserTime.setForeground(GlobalVars.textColor);
		lblUserTime.setBounds(185, 28, 61, 16);
		panel.add(lblUserTime);

		btnPlayAgain = new JButton("Rigioca la partita");
		btnPlayAgain.setBounds(568, 360, 230, 29);
		add(btnPlayAgain);

		btnBackGiocaPanel = new JButton("Torna alla selezione dei livelli");
		btnBackGiocaPanel.setBounds(568, 400, 230, 29);
		add(btnBackGiocaPanel);
		
		btnEsci = new JButton("Esci");
		btnEsci.setForeground(Color.RED);
		btnEsci.setBounds(624, 440, 117, 29);
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

	public JButton getBtnPlayAgain() {
		return btnPlayAgain;
	}

	public JButton getBtnEsci() {
		return btnEsci;
	}
}
