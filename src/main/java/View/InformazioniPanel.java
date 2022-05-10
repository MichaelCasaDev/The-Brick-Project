package View;

import Main.GlobalVars;

import javax.swing.*;
import java.awt.Font;

public class InformazioniPanel extends JPanel {
	private JLabel lblNewLabel;
	private JScrollPane scrollPane;
	private JLabel lblImg;
	private JButton btnTornaIndietro;

	/**
	 * Create the panel.
	 */
	public InformazioniPanel() {
		setBackground(GlobalVars.backgroundColor);
		setLayout(null);
		
		lblNewLabel = new JLabel("Informazioni");
		lblNewLabel.setForeground(GlobalVars.textColor);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		lblNewLabel.setBounds(608, 64, 149, 30);
		add(lblNewLabel);

		lblImg = new JLabel(new ImageIcon(GlobalVars.dirBase + "images/informazioni.png"));

		scrollPane = new JScrollPane(lblImg);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(64, 158, 1238, 453);
		scrollPane.setBorder(null);
		add(scrollPane);
		
		btnTornaIndietro = new JButton("<- Torna indietro");
		btnTornaIndietro.setBounds(64, 675, 200, 29);
		add(btnTornaIndietro);
	}

	public JButton getBtnTornaIndietro() {
		return btnTornaIndietro;
	}
}
