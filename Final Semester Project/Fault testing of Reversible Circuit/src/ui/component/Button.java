package ui.component;

import java.awt.Font;

import javax.swing.JButton;

public class Button extends JButton {
	private static final long serialVersionUID = 1L;

	public Button(String title, int x, int y, int fontSize) {
		setText(title);
		setBounds(x, y, 200, 50);
		setFont(new Font("Cambria", Font.BOLD + Font.ITALIC, fontSize));
	}
}
