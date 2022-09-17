package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import ui.component.Button;
import ui.component.Frame;
import ui.component.Label;

public class HomeFrame {

	private Frame mainFrame;
	private Label title;
	private Button circuit;
	private Button test_set;
	private Button fault_model;
	private Button check;
	private CircuitFrame cFrame;
	private TestsetFrame tFrame;
	private FaultModelFrame fMFrame;

	private int[][] circuitData;
	private int[][] testSetData;
	private String faultModel;

	public HomeFrame() {
		mainFrame = new Frame("Input GUI of Reversible Circuit");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		title = new Label("Give the inputs for checking the faults in Reversible Circuit - ", 900, 150, 28);
		title.setHorizontalAlignment(JLabel.CENTER);
		mainFrame.add(title);

		circuit = new Button("Circuit Input", 350, 200, 16);
		mainFrame.add(circuit);

		test_set = new Button("Test Set Input", 350, 290, 16);
		mainFrame.add(test_set);

		fault_model = new Button("Fault Model Input", 350, 380, 16);
		mainFrame.add(fault_model);

		check = new Button("Check The Circuit", 650, 290, 16);
		mainFrame.add(check);

		circuitData = null;
		testSetData = null;
		faultModel = null;

		circuit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cFrame = new CircuitFrame();
			}
		});

		test_set.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tFrame = new TestsetFrame();
			}
		});

		fault_model.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fMFrame = new FaultModelFrame();
			}
		});

		check.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(cFrame == null || (circuitData = cFrame.getCircuitData()) == null)
					JOptionPane.showMessageDialog(mainFrame, "No circuit provided");
				else if (tFrame == null || (testSetData = tFrame.getTestSetData()) == null)
					JOptionPane.showMessageDialog(mainFrame, "No test set provided");
				else if ( fMFrame == null || (faultModel = fMFrame.getFaultModel()) == null || faultModel.isEmpty())
					JOptionPane.showMessageDialog(mainFrame, "No fault model selected");
				else if (circuitData.length != testSetData.length)
					JOptionPane.showMessageDialog(mainFrame, "Incompatible circuit and test set");
				else
					new OutputFrame(circuitData, testSetData, faultModel);
			}
		});
	}

	public void showCircuit() {
		if (circuitData == null)
			System.out.println("circuit is null");
		else {
			for (int r = 0; r < circuitData.length; r++) {
				for (int c = 0; c < circuitData[0].length; c++) {
					System.out.print(circuitData[r][c] + "\t");
				}
				System.out.println();
			}
		}
	}

	public void showTestSet() {
		if (testSetData == null)
			System.out.println("Test set is null");
		else {
			for (int r = 0; r < testSetData.length; r++) {
				for (int c = 0; c < testSetData[0].length; c++) {
					System.out.print(testSetData[r][c] + "\t");
				}
				System.out.println();
			}
		}
	}
}
