package View;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class ImpostazioniPanel extends JPanel {
	private JLabel lblNewLabel;
	private JComboBox comboBoxUtenti;
	private JLabel lblNewLabel_1;
	private JLabel lblUsername;
	private JTextField textFieldUsername;
	private JLabel lblNewLabel_3;
	private JCheckBox chckbxSuoni;
	private JButton btnTornaIndietro;
	private JButton btnReset;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_5;
	private JLabel lblNewLabel_6;
	private JLabel lblNewLabel_7;
	private JSeparator separator;
	private JLabel lblTotBricksBreak;
	private JLabel lblTotPlayGame;
	private JLabel lblLevel;
	private JButton btnAggiungi;
	private JButton btnSalva;
	private JButton btnRimuovi;

	/**
	 * Create the panel.
	 */
	public ImpostazioniPanel() {
		setLayout(null);

		lblNewLabel = new JLabel("Impostazioni");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		lblNewLabel.setBounds(607, 64, 151, 30);
		add(lblNewLabel);

		comboBoxUtenti = new JComboBox();
		comboBoxUtenti.setBounds(64, 156, 200, 27);
		add(comboBoxUtenti);

		lblNewLabel_1 = new JLabel("Seleziona un utente");
		lblNewLabel_1.setBounds(64, 128, 130, 16);
		add(lblNewLabel_1);

		lblUsername = new JLabel("Username");
		lblUsername.setBounds(367, 128, 62, 16);
		add(lblUsername);

		textFieldUsername = new JTextField();
		textFieldUsername.setBounds(441, 123, 130, 26);
		add(textFieldUsername);
		textFieldUsername.setColumns(10);

		lblNewLabel_3 = new JLabel("Suoni");
		lblNewLabel_3.setBounds(367, 160, 61, 16);
		add(lblNewLabel_3);

		chckbxSuoni = new JCheckBox("Attivati");
		chckbxSuoni.setBounds(441, 156, 128, 23);
		add(chckbxSuoni);

		btnTornaIndietro = new JButton("<- Torna indietro");
		btnTornaIndietro.setBounds(64, 675, 200, 29);
		add(btnTornaIndietro);

		btnReset = new JButton("Reset statistiche");
		btnReset.setBounds(367, 244, 204, 29);
		add(btnReset);

		lblNewLabel_2 = new JLabel("Statistiche");
		lblNewLabel_2.setBounds(711, 128, 66, 16);
		add(lblNewLabel_2);

		lblNewLabel_5 = new JLabel("Totale blocchi rotti");
		lblNewLabel_5.setBounds(711, 160, 120, 16);
		add(lblNewLabel_5);

		lblNewLabel_6 = new JLabel("Totale partite giocate");
		lblNewLabel_6.setBounds(711, 191, 134, 16);
		add(lblNewLabel_6);

		lblNewLabel_7 = new JLabel("Livello attuale");
		lblNewLabel_7.setBounds(711, 219, 88, 16);
		add(lblNewLabel_7);

		separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(857, 156, 12, 79);
		add(separator);

		lblTotBricksBreak = new JLabel("-");
		lblTotBricksBreak.setBounds(881, 160, 61, 16);
		add(lblTotBricksBreak);

		lblTotPlayGame = new JLabel("-");
		lblTotPlayGame.setBounds(881, 191, 61, 16);
		add(lblTotPlayGame);

		lblLevel = new JLabel("-");
		lblLevel.setBounds(881, 219, 61, 16);
		add(lblLevel);

		btnAggiungi = new JButton("+");
		btnAggiungi.setBounds(276, 155, 45, 29);
		add(btnAggiungi);

		btnSalva = new JButton("Salva");
		btnSalva.setBounds(367, 203, 204, 29);
		add(btnSalva);

		btnRimuovi = new JButton("-");
		btnRimuovi.setBounds(276, 186, 45, 29);
		add(btnRimuovi);

	}

	public JComboBox getComboBoxUtenti() {
		return comboBoxUtenti;
	}

	public JTextField getTextFieldUsername() {
		return textFieldUsername;
	}

	public JCheckBox getChckbxSuoni() {
		return chckbxSuoni;
	}

	public JButton getBtnTornaIndietro() {
		return btnTornaIndietro;
	}

	public JButton getBtnReset() {
		return btnReset;
	}

	public JLabel getLblTotBricksBreak() {
		return lblTotBricksBreak;
	}

	public JLabel getLblTotPlayGame() {
		return lblTotPlayGame;
	}

	public JLabel getLblLevel() {
		return lblLevel;
	}

	public JButton getBtnAggiungi(){
		return btnAggiungi;
	}

	public JButton getBtnRimuovi(){
		return btnRimuovi;
	}

	public JButton getBtnSalva() {
		return btnSalva;
	}
}
