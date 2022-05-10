package View;

import Main.GlobalVars;

import javax.swing.*;
import java.awt.*;

public class ComeSiGiocaPanel extends JPanel {
	private JLabel lblNewLabel;
	private JScrollPane scrollPane;
	private JLabel lblImg;
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

		lblImg = new JLabel(new ImageIcon(GlobalVars.dirBase + "images/come_si_gioca.png"));

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
