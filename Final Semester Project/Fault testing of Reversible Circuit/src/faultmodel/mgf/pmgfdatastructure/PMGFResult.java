package faultmodel.mgf.pmgfdatastructure;

import java.util.HashMap;
import java.util.Map;

import reversiblecircuit.nctlibrary.Circuit;
import reversiblecircuit.nctlibrary.CircuitException;
import reversiblegate.mctgate.GateControl;
import testset.Testset;

/**
 * Data structure use to store the result of PMGF test.
 * 
 * <p>
 * It uses {@link Node} objects to construct a graph which is essentially used
 * to store the result.
 * 
 * @author AKASH
 *
 */
public class PMGFResult {
	private Node circuit, testset;
	/**
	 * Constant which represents a gate with no control input.
	 */
	public final int NO_CONTROL;

	/**
	 * Provided with a {@link Circuit} and a {@link Testset} it initialize the data
	 * structure.
	 * 
	 * @param cir  {@link Circuit} to be tested.
	 * @param tset {@link Testset} which will be used to test.
	 * @throws PMGFException thrown if any of the arguments are null or any internal
	 *                       {@linkplain CircuitException} occurs while fetching the
	 *                       gates
	 */
	public PMGFResult(Circuit cir, Testset tset) throws PMGFException {
		if (cir == null || tset == null)
			throw new PMGFException("Either null circuit or null testset");
		NO_CONTROL = -1;

		try {
			circuit = new Node(0);
			// Node creation for each gate
			int noOfGates = cir.noOfGates();
			for (int g = 0; g < noOfGates; g++) {
				Node gateNode = new Node(g);
				// Node creation for each control
				GateControl[] control = cir.getGate(g).getControl();
				boolean haveControl = false;
				for (int c = 0; c < control.length; c++) {
					if (control[c] == GateControl.CONTROL_INPUT) {
						Node controlNode = new Node(c);
						gateNode.setChildWithParent(c, controlNode);
						haveControl = true;
					}
				}
				if (haveControl == false) {
					Node controlNode = new Node(NO_CONTROL);
					gateNode.setChildWithParent(NO_CONTROL, controlNode);
				}
				// Gate node attachment to circuit node
				circuit.setChildWithParent(g, gateNode);
			}
		} catch (CircuitException exp) {
			PMGFException pExp = new PMGFException("Circuit Exception occured");
			pExp.initCause(exp);
			throw pExp;
		}

		testset = new Node(0);
		int noOfTestVector = tset.size();
		for (int t = 0; t < noOfTestVector; t++) {
			Node tVecNode = new Node(t);
			testset.setChildWithParent(t, tVecNode);
		}
	}

	/**
	 * Links a particular control input of a particular gate with a particular test
	 * vector.
	 * 
	 * <p>
	 * Creates a link from a control node to a test vector node as well as from that
	 * test vector node to that particular control node to show that a particular
	 * control is tested by a particular test vector.
	 * <p>
	 * Each control must be associated with a gate and thus three parameters are
	 * required.
	 * 
	 * @param gateNo    Gate number of the circuit.
	 * @param controlNo Control point number of the gate.
	 * @param tVecNo    Test vector number of the test set.
	 * @throws PMGFException thrown if any of the arguments provided appears to be
	 *                       invalid with respect to gate or test set.
	 */
	public void set(int gateNo, int controlNo, int tVecNo) throws PMGFException {
		Node gate = circuit.getChild(gateNo);
		if (gate == null)
			throw new PMGFException("Invalid gate number");
		Node control = gate.getChild(controlNo);
		if (control == null)
			throw new PMGFException("Invalid gate control");
		Node testVector = testset.getChild(tVecNo);
		if (testVector == null)
			throw new PMGFException("Invalid test vector number");
		control.setOnlyChild(tVecNo, testVector);
		testVector.setOnlyChild(gate.getData(), control);
	}

	/**
	 * It is similar to the {@link #set(int, int, int) set} method except it is used
	 * for CNOT gates.
	 * <p>
	 * CNOT gate do not have any control input line, thus this method do not take
	 * any control number.
	 * 
	 * @param gateNo Gate number of the circuit.
	 * @param tVecNo Test vector number of the test set.
	 * @throws PMGFException thrown if any of the arguments provided appears to be
	 *                       invalid with respect to gate or test set.
	 */
	public void setForNoControl(int gateNo, int tVecNo) throws PMGFException {
		set(gateNo, NO_CONTROL, tVecNo);
	}

	/**
	 * Provided with the gate number, it returns a {@link Map} containing
	 * information about which gate control line is tested by which test vectors.
	 * 
	 * @param gateNo Gate number of the circuit.
	 * @return {@link Map} whose key represents the control number and value
	 *         represents the list of test vectors which tests the control of the
	 *         gate
	 * @throws PMGFException thrown for invalid gate number
	 */
	public Map<Integer, int[]> gateToGateCtrlTVecs(int gateNo) throws PMGFException {
		Node gate = circuit.getChild(gateNo);
		if (gate == null)
			throw new PMGFException("Invalid gate number");
		Map<Integer, int[]> result = new HashMap<>();
		for (int controlNo : gate.getAllChildKey()) {
			result.put(controlNo,
					gate.getChild(controlNo).getAllChildKey().stream().mapToInt(Integer::intValue).toArray());
		}
		return result;
	}

	/**
	 * Provided with the test vector number, it returns a {@link Map} containing
	 * information about which gates and their controls are tested by the test
	 * vector.
	 * 
	 * @param tVecNo Test vector number of the test set.
	 * @return {@link Map} whose keys are the gate numbers and values are the
	 *         control numbers.
	 * @throws PMGFException thrown for invalid test vector number.
	 */
	public Map<Integer, Integer> tVecToGateGateCtrl(int tVecNo) throws PMGFException {
		Node tVec = testset.getChild(tVecNo);
		if (tVec == null)
			throw new PMGFException("Invalid Test Vector number");
		Map<Integer, Integer> result = new HashMap<>();
		for (Integer gateNo : tVec.getAllChildKey()) {
			result.put(gateNo, tVec.getChild(gateNo).getData());
		}
		return result;
	}

	/**
	 * Returns true if the entire circuit is tested otherwise returns false.
	 * <p>
	 * True if all the controls of all the gates are tested by at least one test
	 * vector.
	 * 
	 * @return true if the circuit is tested otherwise false.
	 */
	public boolean isCircuitTested() {
		for (Integer gateNo : circuit.getAllChildKey()) {
			Node gate = circuit.getChild(gateNo);
			for (Integer controlNo : gate.getAllChildKey())
				if (!gate.getChild(controlNo).haveChild())
					return false;
		}
		return true;
	}
}
