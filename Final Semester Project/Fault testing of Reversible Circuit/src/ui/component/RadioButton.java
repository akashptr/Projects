package ui.component;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JRadioButton;

public class RadioButton extends JRadioButton {
	private static final long serialVersionUID = 1L;

	public RadioButton(String title, int y) {
		setText(title);
		setBounds(100, y, 300, 40);
		setFont(new Font("Cambria", Font.BOLD + Font.ITALIC, 16));
		setBackground(new java.awt.Color(7, 74, 51));
		setForeground(Color.WHITE);
	}
}
