package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import ui.component.Button;
import ui.component.Frame;
import ui.component.Label;
import ui.component.Table;
import ui.component.TextField;

public class TestsetFrame {

	private Frame testSetFrame;
	private Label tslabel;
	private Label inputLines;
	private Label testSet;
	private TextField inputField;
	private TextField testSetField;
	private Button create;
	private Label tstitle;
	private Table table;
	private Button submit;
	private Button back;

	private int[][] testSetData;
	private int row, column;
	private Label tVTitle;
	private Label inputTitle;

	public TestsetFrame() {
		testSetData = null;
		row = 0;
		column = 0;

		testSetFrame = new Frame("Test Set Input of Reversible Circuit");

		tslabel = new Label("Enter the Input Lines And Number of Test Set - ", 700, 100, 28);
		tslabel.setHorizontalAlignment(JLabel.LEFT);
		testSetFrame.add(tslabel);

		inputLines = new Label("Input Lines - ", 200, 200, 16);
		tslabel.setHorizontalAlignment(JLabel.LEFT);
		testSetFrame.add(inputLines);

		testSet = new Label("Number of Test - ", 200, 300, 16);
		tslabel.setHorizontalAlignment(JLabel.LEFT);
		testSetFrame.add(testSet);

		inputField = new TextField(160, 93);
		testSetFrame.add(inputField);

		testSetField = new TextField(160, 144);
		testSetFrame.add(testSetField);

		create = new Button("Create Table", 350, 100, 16);
		testSetFrame.add(create);

		tstitle = new Label("Enter The Values Of The Table - ", 500, 500, 28);
		tstitle.setVisible(false);
		testSetFrame.add(tstitle);
		
		tVTitle = new Label("Test vectors", 500, 500, 28);
		tVTitle.setHorizontalAlignment(JLabel.LEFT);
		tVTitle.setBounds(400, 50, 500, 500);
		tVTitle.setVisible(false);
		testSetFrame.add(tVTitle);
		
		inputTitle = new Label("Input lines(0 and 1 only)", 200, 650, 20);
		inputTitle.setHorizontalAlignment(JLabel.LEFT);
		inputTitle.setBounds(10, 70, 300, 650);
		inputTitle.setVisible(false);
		testSetFrame.add(inputTitle);

		back = new Button("Back", 500, 600, 16);
		back.setVisible(true);
		testSetFrame.add(back);

		submit = new Button("Submit", 200, 600, 16);
		submit.setVisible(false);
		testSetFrame.add(submit);

		create.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (inputField.getText().isBlank())
					JOptionPane.showMessageDialog(testSetFrame, "Input line field is empty");
				else if (Integer.parseInt(inputField.getText()) <= 0)
					JOptionPane.showMessageDialog(testSetFrame,
							"Number of input lines cannot be less than or equal to 0");
				else if (testSetField.getText().isBlank())
					JOptionPane.showMessageDialog(testSetFrame, "Test field is empty");
				else if (Integer.parseInt(testSetField.getText()) <= 0)
					JOptionPane.showMessageDialog(testSetFrame, "Number of tests cannot be less than or equal to 0");
				else {
					row = Integer.parseInt(inputField.getText());
					column = Integer.parseInt(testSetField.getText());
					table = new Table(row, column);
					JScrollPane pane = new JScrollPane(table);
					pane.setBounds(300, 350, 300, 160);
					testSetFrame.getContentPane().add(pane);
					tstitle.setVisible(true);
					tVTitle.setVisible(true);
					inputTitle.setVisible(true);
					table.setVisible(true);
					submit.setVisible(true);
				}
			}

		});

		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean valid = true;
				OUTER_LOOP: for (int c = 0; c < column; c++) {
					for (int r = 0; r < row; r++) {
						if ((table.getValueAt(r, c) == null) || ((String) table.getValueAt(r, c)).isBlank()) {
							JOptionPane.showMessageDialog(testSetFrame, "cell{" + r + "}{" + c + "} is empty");
							valid = false;
							break OUTER_LOOP;
						} else if ((Integer.parseInt((String) table.getValueAt(r, c)) < 0)
								|| (Integer.parseInt((String) table.getValueAt(r, c)) > 1)) {
							JOptionPane.showMessageDialog(testSetFrame,
									"cell{" + r + "}{" + c + "} contains invalid value");
							valid = false;
							break OUTER_LOOP;
						}
					}
				}

				if (valid == true) {
					testSetData = new int[row][column];
					for (int r = 0; r < row; r++) {
						for (int c = 0; c < column; c++) {
							testSetData[r][c] = Integer.parseInt((String) table.getValueAt(r, c));
						}
					}
					JOptionPane.showMessageDialog(testSetFrame, "Data saved successfully");
					testSetFrame.dispose();
				}

			}
		});

		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				testSetFrame.dispose();
			}
		});

	}

	public int[][] getTestSetData() {
		return testSetData;
	}

}
