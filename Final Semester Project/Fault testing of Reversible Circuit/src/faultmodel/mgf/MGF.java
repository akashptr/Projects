package faultmodel.mgf;

import java.util.Arrays;
import java.util.Map;

import faultmodel.mgf.pmgfdatastructure.PMGFException;
import faultmodel.mgf.pmgfdatastructure.PMGFResult;
import faultmodel.mgf.smgfdatastructure.SMGFException;
import faultmodel.mgf.smgfdatastructure.SMGFResult;
import reversiblecircuit.nctlibrary.Circuit;
import reversiblecircuit.nctlibrary.CircuitException;
import reversiblegate.mctgate.GateControl;
import testset.Testset;
import testset.TestsetException;

/**
 * This class is used to test Missing Gate Fault.
 * <p>
 * This class includes a {@link Circuit} and a {@link Testset}. It also holds
 * {@link SMGFResult} and {@link PMGFResult} to store SMGF and PMGF test results
 * respectively.
 * 
 * @author AKASH
 *
 */
public class MGF {
	private Circuit circuit;
	private Testset testset;
	private SMGFResult smgfResult;
	private PMGFResult pmgfResult;

	/**
	 * Logic for SMGF testing is implemented in this method
	 * 
	 * @param tVecNo test vector number
	 * @param gateNo gate number
	 * @throws MGFException thrown for out of range arguments or any internal
	 *                      {@linkplain CircuitException} or
	 *                      {@linkplain SMGFException} occurs
	 */
	private void smgfLogic(int tVecNo, int gateNo) throws MGFException {
		if (tVecNo < 0 || tVecNo >= testset.size())
			throw new MGFException("Invalid test vector number");
		if (gateNo < 0 || gateNo >= circuit.noOfGates())
			throw new MGFException("Invalid gate number");
		try {
			GateControl[] controls = circuit.getGate(gateNo).getControl();
			boolean[] inBuff = circuit.getGate(gateNo).getInputBuffer();
			boolean andedControlInput = true;
			// Loop for each point in the gate
			for (int p = 0; p < circuit.getOrder(); p++)
				if (controls[p] == GateControl.CONTROL_INPUT)
					andedControlInput &= inBuff[p];
			smgfResult.setResult(tVecNo, gateNo, andedControlInput);
		} catch (CircuitException | SMGFException e) {
			MGFException exp = new MGFException("Internal circuit or SMGF exception");
			exp.initCause(e);
			throw exp;
		}
	}

	/**
	 * Logic for PMGF testing is implemented in this method
	 * 
	 * @param tVecNo test vector number
	 * @param gateNo gate number
	 * @throws MGFException thrown for out of range arguments or any internal
	 *                      {@linkplain CircuitException} or
	 *                      {@linkplain PMGFException} occurs
	 */
	private void pmgfLogic(int tVecNo, int gateNo) throws MGFException {
		if (tVecNo < 0 || tVecNo >= testset.size())
			throw new MGFException("Invalid test vector number");
		if (gateNo < 0 || gateNo >= circuit.noOfGates())
			throw new MGFException("Invalid gate number");

		try {
			GateControl curGateCtrl[] = circuit.getGate(gateNo).getControl();
			boolean ipBuff[] = circuit.getGate(gateNo).getInputBuffer();
			int noOfCtrl = 0;
			int candidateCtrl = -1;
			boolean restCtrl = true;
			int gateOrder = circuit.getOrder();
			for (int ipNo = 0; ipNo < gateOrder; ipNo++) {
				if (curGateCtrl[ipNo] == GateControl.CONTROL_INPUT) {
					noOfCtrl++;
					if (candidateCtrl == -1 && ipBuff[ipNo] == false)
						candidateCtrl = ipNo;
					else
						restCtrl &= ipBuff[ipNo];
				}
			}
			if (noOfCtrl == 0)
				pmgfResult.setForNoControl(gateNo, tVecNo);
			else if (candidateCtrl != -1 && restCtrl == true)
				pmgfResult.set(gateNo, candidateCtrl, tVecNo);
		} catch (CircuitException | PMGFException e) {
			MGFException exp = new MGFException("Internal circuit or PMGF exception");
			exp.initCause(e);
			throw exp;
		}
	}

	/**
	 * It is required to provide a circuit and a test set to create a object of this
	 * class.
	 * <p>
	 * After all validation an object is initialized. If the validation fails
	 * {@link MGFException} is thrown and no object will be created.
	 * 
	 * @param circuit to be tested
	 * @param tSet    test set to test with
	 * @throws MGFException thrown if the arguments are null or any internal
	 *                      {@linkplain SMGFException} or {@linkplain PMGFException}
	 *                      occurs.
	 */
	public MGF(Circuit circuit, Testset tSet) throws MGFException {
		if (circuit == null || tSet == null)
			throw new MGFException("Either circuit is null or testset is null");
		if (circuit.getOrder() != tSet.getOrder())
			throw new MGFException("Circuit and testset are incompatible with each other");
		this.circuit = circuit;
		this.testset = tSet;
		try {
			this.smgfResult = new SMGFResult(this.testset.size(), this.circuit.noOfGates());
			this.pmgfResult = new PMGFResult(this.circuit, this.testset);
		} catch (SMGFException | PMGFException e) {
			MGFException exp = new MGFException("Internal SMGF or PMGF exception occured");
			exp.initCause(e);
			throw exp;
		}
	}

	/**
	 * Tests the circuit using the test set.
	 * <p>
	 * Both SMGF and PMGF test are done and the results are stored in
	 * {@link SMGFResult} and {@link PMGFResult} member variables.
	 * 
	 * @throws MGFException thrown if
	 *                      <ul>
	 *                      <li>any internal {@linkplain CircuitException} occurs
	 *                      while loading the input buffer</li>
	 *                      <li>any internal {@linkplain TestsetException} occurs
	 *                      while fetching test vectors</li>
	 *                      <li>any exception thrown by
	 *                      {@linkplain #smgfLogic(int, int) smgfLogic} or
	 *                      {@linkplain #pmgfLogic(int, int) pmgfLogic}
	 *                      </ul>
	 */
	public void test() throws MGFException {
		int tCount = testset.size();
		int gCount = circuit.noOfGates();
		try {
			// Loop for each test vector
			for (int t = 0; t < tCount; t++) {
				circuit.loadInput(testset.getTestVector(t));
				circuit.propagate();
				// Loop for each gate
				for (int g = 0; g < gCount; g++) {
					smgfLogic(t, g);
					pmgfLogic(t, g);
				}
			}
		} catch (CircuitException | TestsetException e) {
			MGFException exp = new MGFException("Internal circuit or testset exception occured");
			exp.initCause(e);
			throw exp;
		}
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();

		int gCount = circuit.noOfGates();

		try {
			str.append("Gate result\n");
			for (int g = 0; g < gCount; g++) {
				str.append("Gate no: " + g + "\n");
				int[] smgfTVecs = smgfResult.gateToTVecs(g);
				str.append("SMGF Test Vectors: " + Arrays.toString(smgfTVecs) + "\n");

				Map<Integer, int[]> pmgfCtrlTVecs = pmgfResult.gateToGateCtrlTVecs(g);
				if (pmgfCtrlTVecs.get(pmgfResult.NO_CONTROL) == null) {
					str.append("PMGF Test Vectors: ");
					for (int key : pmgfCtrlTVecs.keySet()) {
						str.append("{" + key + ": " + Arrays.toString(pmgfCtrlTVecs.get(key)) + "}");
					}
					str.append("\n");
				}
			}

			/*
			 * int tCount = testset.size(); str.append("\nTestset result\n"); for (int t =
			 * 0; t < tCount; t++) { str.append("Test vector number: " + t + "\n");
			 * str.append("SMGF tested gates: " + Arrays.toString(smgfResult.tVecToGates(t))
			 * + "\n"); str.append("PMGF tested gates: "); Map<Integer, Integer>
			 * pmgfGateGateCtrl = pmgfResult.tVecToGateGateCtrl(t);
			 * str.append(pmgfGateGateCtrl.toString() + "\n"); }
			 */
			str.append("\nCircuit result\n");
			str.append("SMGF tested: " + smgfResult.isCircuitTested() + "\n");
			str.append("PMGF tested: " + pmgfResult.isCircuitTested() + "\n");

			return str.toString();
		} catch (SMGFException | PMGFException e) {
			e.printStackTrace();
		}

		return "";
	}
}
