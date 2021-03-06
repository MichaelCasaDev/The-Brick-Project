package View;

import Main.GlobalVars;
import Model.Level;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.BorderLayout;

public class GiocaPanel extends JPanel {
	private JLabel lblNewLabel;
	private JButton btnGioca;
	private JPanel panel;
	private JScrollPane scrollPane;
	private JList<Level> list;
	private JButton btnTornaIndietro;
	private JPanel panel_1;
	private JButton btnStoria;

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
		
		scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);
		
		list = new JList<>();
		list.setBackground(GlobalVars.backgroundColorAlt);
		list.setForeground(GlobalVars.textColor);
		scrollPane.setViewportView(list);
		
		panel_1 = new JPanel();
		panel_1.setBackground(GlobalVars.backgroundColor);
		panel.add(panel_1, BorderLayout.SOUTH);
		
		btnGioca = new JButton("Avvia livello");
		panel_1.add(btnGioca);
		btnGioca.setEnabled(false);
		
		btnStoria = new JButton("Avvia Storia");
		panel_1.add(btnStoria);
		
		btnTornaIndietro = new JButton("<- Torna indietro");
		btnTornaIndietro.setBounds(64, 675, 200, 29);
		add(btnTornaIndietro);
	}

	public JButton getBtnGioca() {
		return btnGioca;
	}

	public JList<Level> getList() {
		return list;
	}

	public JButton getBtnTornaIndietro() {
		return btnTornaIndietro;
	}

	public JButton getBtnStoria() {
		return btnStoria;
	}
}
