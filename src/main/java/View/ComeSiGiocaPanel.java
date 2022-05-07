package View;

import Main.GlobalVars;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.JButton;

public class ComeSiGiocaPanel extends JPanel {
	private JLabel lblNewLabel;
	private JTextPane txtpnInformazioniDaScrivere;
	private JButton btnTornaIndietro;

	/**
	 * Create the panel.
	 */
	public ComeSiGiocaPanel() {
		setBackground(GlobalVars.backgroundColor);
		setLayout(null);
		
		lblNewLabel = new JLabel("Come si gioca?");
		lblNewLabel.setForeground(GlobalVars.textColor);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		lblNewLabel.setBounds(596, 64, 174, 30);
		add(lblNewLabel);
		
		txtpnInformazioniDaScrivere = new JTextPane();
		txtpnInformazioniDaScrivere.setBackground(GlobalVars.backgroundColorAlt);
		txtpnInformazioniDaScrivere.setForeground(GlobalVars.textColor);
		txtpnInformazioniDaScrivere.setContentType("text/html");
		txtpnInformazioniDaScrivere.setText("<p style='color: white'>si può usare l'HTML</p>");
		txtpnInformazioniDaScrivere.setEditable(false);
		txtpnInformazioniDaScrivere.setBounds(64, 158, 1238, 453);
		add(txtpnInformazioniDaScrivere);
		
		btnTornaIndietro = new JButton("<- Torna indietro");
		btnTornaIndietro.setBounds(64, 675, 200, 29);
		add(btnTornaIndietro);
	}

	public JButton getBtnTornaIndietro() {
		return btnTornaIndietro;
	}
}
