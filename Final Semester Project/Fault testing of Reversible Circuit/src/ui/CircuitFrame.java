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

public class CircuitFrame {

	private Frame circuitFrame;
	private Label circuitFrameHeading;
	private Label labelInputLine;
	private TextField inputField;
	private Label labelGateLine;
	private TextField gateField;
	private Button createButton;
	private Label tableTitle;
	private Label title0;
	private Label title1;
	private Label title2;
	private Table table;
	private Button submit;
	private Button back;
	private JLabel gateTitle;

	private int[][] circuitData;
	private int row, column;

	public CircuitFrame() {

		this.circuitData = null;
		this.row = 0;
		this.column = 0;

		circuitFrame = new Frame("Circuit Input of Reversible Circuit");

		circuitFrameHeading = new Label("Enter circuit data", 700, 100, 28);
		circuitFrameHeading.setHorizontalAlignment(JLabel.LEFT);
		circuitFrame.add(circuitFrameHeading);

		labelInputLine = new Label("Number of input lines", 200, 200, 16);
		circuitFrameHeading.setHorizontalAlignment(JLabel.LEFT);
		circuitFrame.add(labelInputLine);

		inputField = new TextField(120, 93);
		circuitFrame.add(inputField);

		labelGateLine = new Label("Number of gate", 200, 300, 16);
		circuitFrameHeading.setHorizontalAlignment(JLabel.LEFT);
		circuitFrame.add(labelGateLine);

		gateField = new TextField(120, 144);
		circuitFrame.add(gateField);

		createButton = new Button("create Table", 320, 100, 16);
		circuitFrame.add(createButton);

		tableTitle = new Label("Enter The Values Of The Table - ", 500, 500, 28);
		tableTitle.setHorizontalAlignment(JLabel.LEFT);
		tableTitle.setVisible(false);
		circuitFrame.add(tableTitle);
		
		gateTitle = new Label("Gate", 500, 500, 28);
		gateTitle.setHorizontalAlignment(JLabel.LEFT);
		gateTitle.setBounds(300, 50, 500, 500);
		gateTitle.setVisible(false);
		circuitFrame.add(gateTitle);
		
		title0 = new Label("0 -> Null Input", 200, 650, 20);
		title0.setHorizontalAlignment(JLabel.LEFT);
		title0.setBounds(10, 70, 200, 650);
		title0.setVisible(false);
		circuitFrame.add(title0);

		title1 = new Label("1 -> Control Input", 200, 750, 20);
		title1.setHorizontalAlignment(JLabel.LEFT);
		title1.setBounds(10, 100, 200, 650);
		title1.setVisible(false);
		circuitFrame.add(title1);

		title2 = new Label("2 -> Target Input", 200, 850, 20);
		title2.setHorizontalAlignment(JLabel.LEFT);
		title2.setBounds(10, 130, 200, 650);
		title2.setVisible(false);
		circuitFrame.add(title2);

		submit = new Button("Submit", 200, 550, 16);
		submit.setVisible(false);
		circuitFrame.add(submit);

		back = new Button("Back", 500, 550, 16);
		back.setVisible(true);
		circuitFrame.add(back);

		createButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (inputField.getText().isBlank())
					JOptionPane.showMessageDialog(circuitFrame, "Input line field is empty");
				else if (Integer.parseInt(inputField.getText()) <= 0)
					JOptionPane.showMessageDialog(circuitFrame,
							"Number of input lines cannot be less than or equal to 0");
				else if (gateField.getText().isBlank())
					JOptionPane.showMessageDialog(circuitFrame, "Gate field is empty");
				else if (Integer.parseInt(gateField.getText()) <= 0)
					JOptionPane.showMessageDialog(circuitFrame, "Number of gates cannot be less than or equal to 0");
				else {
					row = Integer.parseInt(inputField.getText());
					column = Integer.parseInt(gateField.getText());
					table = new Table(row, column);
					JScrollPane pane = new JScrollPane(table);
					pane.setBounds(200, 350, 300, 160);
					circuitFrame.getContentPane().add(pane);
					tableTitle.setVisible(true);
					table.setVisible(true);
					gateTitle.setVisible(true);
					title0.setVisible(true);
					title1.setVisible(true);
					title2.setVisible(true);
					submit.setVisible(true);
				}
			}
		});

		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean valid = true;
				OUTER_LOOP: for (int c = 0; c < column; c++) {
					int targetCount = 0;
					for (int r = 0; r < row; r++) {
						if ((table.getValueAt(r, c) == null) || ((String) table.getValueAt(r, c)).isBlank()) {
							JOptionPane.showMessageDialog(circuitFrame, "cell{" + r + "}{" + c + "} is empty");
							valid = false;
							break OUTER_LOOP;
						} else if ((Integer.parseInt((String) table.getValueAt(r, c)) < 0)
								|| (Integer.parseInt((String) table.getValueAt(r, c)) > 2)) {
							JOptionPane.showMessageDialog(circuitFrame,
									"cell{" + r + "}{" + c + "} contains invalid value");
							valid = false;
							break OUTER_LOOP;
						} else if(Integer.parseInt((String) table.getValueAt(r, c)) == 2) {
							targetCount++;
						}
					}
					if(targetCount != 1) {
						JOptionPane.showMessageDialog(circuitFrame, "Invalid number of target input at gate " + c);
						valid = false;
						break;
					}
				}

				if(valid == true) {
					circuitData = new int[row][column];
					for (int r = 0; r < row; r++) {
						for(int c = 0; c < column; c++) {
							circuitData[r][c] = Integer.parseInt((String) table.getValueAt(r, c));
						}
					}
					JOptionPane.showMessageDialog(circuitFrame, "Data saved successfully");
					circuitFrame.dispose();
				}
			}
		});

		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				circuitFrame.dispose();
			}
		});

	}

	public int[][] getCircuitData() {
		return circuitData;
	}

}
