package View;

import Main.GlobalVars;

import javax.swing.*;
import java.awt.Font;
import java.awt.Color;
import javax.swing.border.LineBorder;

public class MainMenuPanel extends JPanel {
	private JLabel lblNewLabel;
	private JButton btnEsci;
	private JButton btnImpostazioni;
	private JButton btnComeSiGioca;
	private JButton btnGioca;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblUsername;
	private JButton btnInformazioni;

	/**
	 * Create the panel.
	 */
	public MainMenuPanel() {
		setBackground(GlobalVars.backgroundColor);
		setSize(GlobalVars.frameWidth, GlobalVars.frameHeight);
		setLayout(null);
		
		lblNewLabel = new JLabel("The Brick");
		lblNewLabel.setFont(new Font("Herculanum", Font.PLAIN, 38));
		lblNewLabel.setForeground(GlobalVars.textColor);
		lblNewLabel.setBounds(64, 64, 208, 48);
		add(lblNewLabel);
		
		btnEsci = new JButton("Esci");
		btnEsci.setForeground(Color.RED);
		btnEsci.setBounds(64, 675, 208, 29);
		add(btnEsci);
		
		btnImpostazioni = new JButton("Impostazioni");
		btnImpostazioni.setBounds(64, 589, 208, 29);
		add(btnImpostazioni);
		
		btnComeSiGioca = new JButton("Come si gioca?");
		btnComeSiGioca.setBounds(64, 548, 208, 29);
		add(btnComeSiGioca);
		
		btnGioca = new JButton("Gioca");
		btnGioca.setBounds(64, 479, 208, 29);
		add(btnGioca);
		
		lblNewLabel_1 = new JLabel("Made by Casagrande Michael, Verri Riccardo & Zago Leonardo");
		lblNewLabel_1.setForeground(GlobalVars.textColor);
		lblNewLabel_1.setBounds(916, 688, 386, 16);
		add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel(new ImageIcon(GlobalVars.dirBase + "images/icon.png"));
		lblNewLabel_2.setForeground(Color.ORANGE);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(284, 64, 1076, 554);
		add(lblNewLabel_2);
		
		lblUsername = new JLabel("Logged in as: [username]");
		lblUsername.setForeground(GlobalVars.textColor);
		lblUsername.setBounds(64, 124, 208, 16);
		add(lblUsername);
		
		btnInformazioni = new JButton("Informazioni");
		btnInformazioni.setBounds(64, 634, 208, 29);
		add(btnInformazioni);
	}

	public JButton getBtnEsci() {
		return btnEsci;
	}

	public JButton getBtnImpostazioni() {
		return btnImpostazioni;
	}

	public JButton getBtnComeSiGioca() {
		return btnComeSiGioca;
	}

	public JButton getBtnGioca() {
		return btnGioca;
	}

	public JLabel getLblUsername() {
		return lblUsername;
	}

	public JButton getBtnInformazioni() {
		return btnInformazioni;
	}
}
