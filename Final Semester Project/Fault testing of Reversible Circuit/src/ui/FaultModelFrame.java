package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;

import ui.component.Button;
import ui.component.Frame;
import ui.component.Label;
import ui.component.RadioButton;

public class FaultModelFrame {

	private Frame faultModelFrame;
	private Label choose;
	private RadioButton MGFM;
	private RadioButton SAFM;
	private ButtonGroup singleSelection;
	private Button back;
	private Button submit;

	private String faultModel;

	public FaultModelFrame() {

		faultModel = null;

		faultModelFrame = new Frame("Fault Model Input of Reversible Circuit");

		choose = new Label("Choose the Fault Model from Below - ", 700, 100, 28);
		faultModelFrame.add(choose);

		MGFM = new RadioButton("Missing Gate Fault Model", 200);
		faultModelFrame.add(MGFM);

		SAFM = new RadioButton("Stuck-At Fault Model", 300);
		faultModelFrame.add(SAFM);

		singleSelection = new ButtonGroup();
		singleSelection.add(MGFM);
		singleSelection.add(SAFM);

		back = new Button("Back", 400, 450, 16);
		faultModelFrame.add(back);

		submit = new Button("Submit", 100, 450, 16);
		faultModelFrame.add(submit);

		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (MGFM.isSelected())
					faultModel = "Missing Gate Fault Model";
				else if (SAFM.isSelected())
					faultModel = "Stuck-At Fault Model";
				else
					JOptionPane.showMessageDialog(null, "Select a fault Model");

				if (faultModel != null) {
					JOptionPane.showMessageDialog(null, "Saved Successfully");
					faultModelFrame.dispose();
				}
			}
		});

		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				faultModelFrame.dispose();
			}
		});
	}

	public String getFaultModel() {
		return faultModel;
	}
}
