package View;

import Main.GlobalVars;
import Main.MainRunnable;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.net.URL;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

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

		setBounds(0, 0, GlobalVars.frameWidth, GlobalVars.frameHeight);
		setLocationRelativeTo(null);
		setResizable(false);

		URL resource = MainRunnable.class.getClassLoader().getResource("icon.png");
		if(resource != null) setIconImage(new ImageIcon(resource).getImage());

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		lblNewLabel = new JLabel("Loading...");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel, BorderLayout.CENTER);

		setVisible(true);
	}

	public JPanel getContentPane() {
		return contentPane;
	}
}
