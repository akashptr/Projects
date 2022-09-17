package ui.component;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Frame extends JFrame {
	private static final long serialVersionUID = 1L;

	public Frame(String title) {
		setTitle(title);
		setLayout(null);
		setSize(1600, 900);
		setVisible(true);
		getContentPane().setBackground(new java.awt.Color(7, 74, 51));
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				int option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Close Information",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (option == JOptionPane.YES_OPTION)
					dispose();
			}
		});
	}
}
