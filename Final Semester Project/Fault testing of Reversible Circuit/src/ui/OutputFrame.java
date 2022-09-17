package ui;

import javax.swing.JLabel;
import javax.swing.JScrollPane;

import bit.BitTransform;
import faultmodel.mgf.MGF;
import faultmodel.mgf.MGFException;
import reversiblecircuit.nctlibrary.Circuit;
import reversiblecircuit.nctlibrary.CircuitException;
import testset.Testset;
import testset.TestsetException;
import ui.component.Frame;
import ui.component.Label;
import ui.component.Table;

public class OutputFrame {

	private Frame checkFrame;
	private Label circuitLabel;
	private int circuitLabelcol;
	private Table cTable;
	private Label testSetLabel;
	private int testSetCol;
	private Table tsTable;
	private Label faultmodel;
	private Label fm;
	private Label output;

	private Circuit createCircuit(int[][] data) throws CircuitException {
		int row = data.length;
		int col = data[0].length;
		Circuit cir = null;
		for (int c = 0; c < col; c++) {
			int[] gate = new int[row];
			for (int r = 0; r < row; r++) {
				gate[r] = data[r][c];
			}
			if (c == 0)
				cir = new Circuit(gate);
			else
				cir.add(gate);
		}
		return cir;
	}

	private Testset createTestset(int[][] data) throws TestsetException {
		int row = data.length;
		int col = data[0].length;
		Testset tSet = null;
		for (int c = 0; c < col; c++) {
			boolean[] tVec = new boolean[row];
			for (int r = 0; r < row; r++) {
				tVec[r] = BitTransform.intToBool(data[r][c]);
			}
			if (c == 0)
				tSet = new Testset(tVec);
			else
				tSet.add(tVec);
		}
		return tSet;
	}
	
	public OutputFrame(int[][] circuit, int[][] testSet, String faultModel) {

		checkFrame = new Frame("Output GUI Of Reversible circuitLabel");

		circuitLabel = new Label("circuitLabel - ", 900, 100, 28);
		circuitLabel.setHorizontalAlignment(JLabel.LEFT);
		checkFrame.add(circuitLabel);

		circuitLabelcol = circuit[0].length;
		String[] circuitLabelcolumn = new String[circuitLabelcol];
		for (int i = 0; i < circuitLabelcol; i++)
			circuitLabelcolumn[i] = "Gate " + i;

		cTable = new Table(circuit.length, circuit[0].length);
		cTable.loadTable(circuit);
		JScrollPane circuitLabelpane = new JScrollPane(cTable);
		circuitLabelpane.setBounds(50, 80, 200, 100);
		checkFrame.getContentPane().add(circuitLabelpane);

		testSetLabel = new Label("Test Set - ", 900, 100, 28);
		testSetLabel.setHorizontalAlignment(JLabel.CENTER);
		checkFrame.add(testSetLabel);

		testSetCol = testSet[0].length;
		String[] testSetColumn = new String[testSetCol];
		for (int i = 0; i < testSetCol; i++)
			testSetColumn[i] = "Test Set" + i;

		tsTable = new Table(testSet.length, testSet[0].length);
		tsTable.loadTable(testSet);
		JScrollPane testSetLabelpane = new JScrollPane(tsTable);
		testSetLabelpane.setBounds(460, 80, 200, 100);
		checkFrame.getContentPane().add(testSetLabelpane);

		faultmodel = new Label("Fault Model - ", 900, 100, 28);
		faultmodel.setHorizontalAlignment(JLabel.RIGHT);
		checkFrame.add(faultmodel);

		fm = new Label(faultModel, 1100, 200, 24);
		fm.setHorizontalAlignment(JLabel.RIGHT);
		checkFrame.add(fm);

		try {
			String outputString;
			if(faultModel.equals("Stuck-At Fault Model"))
				outputString = "Under construction";
			else {
				Circuit newCircuit = createCircuit(circuit);
				Testset newTestset = createTestset(testSet);
				MGF mgfObj = new MGF(newCircuit, newTestset);
				mgfObj.test();
				outputString = "<html>" + mgfObj.toString().replaceAll("\n", "<br>");
				
			}
			output = new Label(outputString, 900, 500, 20);
			output.setHorizontalAlignment(JLabel.LEFT);
			output.setBounds(100, 200, 900, 500);
			checkFrame.add(output);
		} catch(CircuitException | TestsetException exp) {
			exp.printStackTrace();
		} catch (MGFException exp) {
			exp.printStackTrace();
		}
	}
}
