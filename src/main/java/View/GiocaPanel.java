package View;

import Main.GlobalVars;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.UIManager;

public class GiocaPanel extends JPanel {
	private JLabel lblNewLabel;
	private JButton btnGioca;
	private JPanel panel;
	private JScrollPane scrollPane;
	private JList list;
	private JButton btnTornaIndietro;

	/**
	 * Create the panel.
	 */
	public GiocaPanel() {
		setBackground(GlobalVars.backgroundColor);
		setLayout(null);
		
		lblNewLabel = new JLabel("Selezione livello");
		lblNewLabel.setForeground(GlobalVars.textColor);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		lblNewLabel.setBounds(590, 64, 186, 30);
		add(lblNewLabel);
		
		panel = new JPanel();
		panel.setBackground(GlobalVars.backgroundColor);
		panel.setBounds(64, 158, 1238, 453);
		add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		btnGioca = new JButton("Avvia livello");
		btnGioca.setEnabled(false);
		panel.add(btnGioca, BorderLayout.SOUTH);
		
		scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);
		
		list = new JList();
		list.setBackground(GlobalVars.backgroundColorAlt);
		list.setForeground(GlobalVars.textColor);
		scrollPane.setViewportView(list);
		
		btnTornaIndietro = new JButton("<- Torna indietro");
		btnTornaIndietro.setBounds(64, 675, 200, 29);
		add(btnTornaIndietro);

	}

	public JButton getBtnGioca() {
		return btnGioca;
	}

	public JList getList() {
		return list;
	}

	public JButton getBtnTornaIndietro() {
		return btnTornaIndietro;
	}	
}
