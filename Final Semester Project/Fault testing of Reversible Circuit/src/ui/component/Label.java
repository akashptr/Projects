package ui.component;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class Label extends JLabel{
	private static final long serialVersionUID = 1L;

    public Label(String title, int width, int height, int fontSize) {
        setText(title);
        setSize(width, height);
        setFont(new Font("Cambria", Font.BOLD + Font.ITALIC, fontSize));
        setForeground(Color.WHITE);
    }
}
