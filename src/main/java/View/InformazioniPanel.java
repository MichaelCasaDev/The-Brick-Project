package View;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSpinner;
import javax.swing.JTextPane;
import javax.swing.JButton;

public class InformazioniPanel extends JPanel {
	private JLabel lblNewLabel;
	private JTextPane txtpnInformazioniDaScrivere;
	private JButton btnTornaIndietro;

	/**
	 * Create the panel.
	 */
	public InformazioniPanel() {
		setLayout(null);
		
		lblNewLabel = new JLabel("Informazioni");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		lblNewLabel.setBounds(608, 64, 149, 30);
		add(lblNewLabel);
		
		txtpnInformazioniDaScrivere = new JTextPane();
		txtpnInformazioniDaScrivere.setContentType("text/html");
		txtpnInformazioniDaScrivere.setText("<p>si può usare l'HTML</p>");
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