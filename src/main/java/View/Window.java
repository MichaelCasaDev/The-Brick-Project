package View;

import Main.GlobalVars;

import java.awt.*;
import java.awt.event.WindowAdapter;

import javax.swing.*;

public class Window extends JFrame {
	private JPanel contentPane;
	private JLabel lblNewLabel;

	/**
	 * Create the frame.
	 */
	public Window(WindowAdapter windowAdapter) {
		setTitle("The Brick");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(windowAdapter);

		setBackground(GlobalVars.backgroundColorAlt);

		setBounds(0, 0, GlobalVars.frameWidth, GlobalVars.frameHeight);
		setLocationRelativeTo(null);
		setResizable(false);

		setIconImage(new ImageIcon(GlobalVars.dirBase + "images/icon.png").getImage());

		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		lblNewLabel = new JLabel("Loading...");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel, BorderLayout.CENTER);

		setVisible(true);
	}

	public void setPane(JPanel p) {
		setContentPane(p);

		p.setFocusable(true);
		p.requestFocus();
		p.requestFocusInWindow();
		revalidate();
	}
}
