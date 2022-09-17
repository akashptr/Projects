package reversiblecircuit.nctlibrary;

import java.util.ArrayList;
import java.util.Arrays;

import reversiblegate.mctgate.GateControl;
import reversiblegate.mctgate.GateException;
import reversiblegate.mctgate.MCTGate;

/**
 * This class represents a reversible circuit consisting only the
 * {@link reversiblegate.mctgate.MCTGate MCTGate}
 * 
 * @author AKASH
 *
 */
public class Circuit {

	// Private members
	private ArrayList<MCTGate> gate;
	private final int order;

	/**
	 * Checks if the order i.e. the number of input lines are same for all gates.
	 * 
	 * @param control gate input lines
	 * @return true if order is consistent otherwise false.
	 * @throws CircuitException thrown if the argument is null or the order do not
	 *                          match
	 */
	private boolean verifyOrder(int[] control) throws CircuitException {
		if (control == null || control.length != order)
			throw new CircuitException("Order of the gate and order of the circuit do not match");
		return true;
	}

	/**
	 * Creates a Circuit object provided with the first gate control.
	 * <p>
	 * Integer array representing the input point or {@link GateControl} has to be
	 * provided. Creates the circuit only when the argument is valid otherwise
	 * throws {@link CircuitException}, thus a circuit must have at least one valid
	 * MCT gate.
	 * 
	 * @param control gate input lines
	 * @throws CircuitException thrown if any internal {@link GateException} occurs
	 */
	public Circuit(int[] control) throws CircuitException {
		try {
			MCTGate firstGate = new MCTGate(control);
			gate = new ArrayList<>();
			gate.add(firstGate);
			order = firstGate.getOrder();
		} catch (GateException exp) {
			CircuitException cExp = new CircuitException("First gate cannot be created");
			cExp.initCause(exp);
			throw cExp;
		}
	}

	/**
	 * Returns the {@link MCTGate} positioned at the index.
	 * 
	 * @param index of the gate(starting is 0)
	 * @return {@link MCTGate}
	 * @throws CircuitException thrown when the index is invalid.
	 */
	public MCTGate getGate(int index) throws CircuitException {
		if (index < 0 || index >= gate.size())
			throw new CircuitException("Invalid gate number");
		return gate.get(index);
	}

	/**
	 * Returns the number of input lines of the circuit.
	 * 
	 * @return number of input lines
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * Returns the number of gates in the circuit.
	 * 
	 * @return number of gates present in the circuit.
	 */
	public int noOfGates() {
		return gate.size();
	}

	/**
	 * Loads the input of the circuit.
	 * <p>
	 * Copies the content of the argument to the input buffer of the first gate of
	 * the circuit.
	 * 
	 * @param input boolean array containing inputs for the circuit input lines.
	 * @throws CircuitException thrown if any internal {@link GateException} occurs
	 *                          while loading the input buffer of the
	 *                          {@link MCTGate}
	 */
	public void loadInput(boolean[] input) throws CircuitException {
		try {
			gate.get(0).setInputBuffer(input);
		} catch (GateException e) {
			CircuitException cExp = new CircuitException("Input Cannot be loaded");
			cExp.initCause(e);
			throw cExp;
		}
	}

	/**
	 * Loads the output of the circuit.
	 * <p>
	 * Copies the content of the argument to the output buffer of the last gate of
	 * the circuit. Useful for reverse propagation.
	 * 
	 * @param output boolean array containing inputs for the circuit output lines.
	 * @throws CircuitException thrown if any internal {@link GateException} occurs
	 *                          while loading the output buffer of the
	 *                          {@link MCTGate}
	 */
	public void loadOutput(boolean[] output) throws CircuitException {
		try {
			gate.get(gate.size() - 1).setOutputBuffer(output);
		} catch (GateException e) {
			CircuitException cExp = new CircuitException("Output cannot be loaded");
			cExp.initCause(e);
			throw cExp;
		}
	}

	/**
	 * Add a MCT gate.
	 * <p>
	 * This method adds a MCT gate to the circuit. The gate should be provided as
	 * integer array.
	 * 
	 * @param control integer array representing gate input points.
	 * @throws CircuitException thrown if order of the new gate do not match with
	 *                          the circuit or if any internal
	 *                          {@linkplain GateException} occurs while adding the
	 *                          gate
	 */
	public void add(int[] control) throws CircuitException {
		// Check order
		verifyOrder(control);
		try {
			// Create gate
			MCTGate newGate = new MCTGate(control);
			// Add to the circuit
			gate.add(newGate);
		} catch (GateException e) {
			CircuitException cExp = new CircuitException("Gate cannot be added");
			cExp.initCause(e);
			throw cExp;
		}
	}

	/**
	 * Clears all the buffers in the circuit.
	 */
	public void reset() {
		for (MCTGate g : gate) {
			g.clearBuffers();
		}
	}

	/**
	 * Propagate the input through the circuit.
	 * <p>
	 * It propagates the input from the first gate through all the gates in the
	 * circuit in a single call. thus each gate buffer stores the immediate
	 * propagation result.
	 */
	public void propagate() {
		int size = gate.size();
		try {
			for (int i = 0; i < size; i++) {
				if (i != 0)
					gate.get(i).setInputBuffer(gate.get(i - 1).getOutputBuffer());
				gate.get(i).propagate();
			}
		} catch (GateException exp) {
			reset();
			exp.printStackTrace();
		}
	}

	/**
	 * Propagation in reverse order.
	 * 
	 * @see #propagate() propagate
	 */
	public void reversePropagate() {
		int size = gate.size();
		try {
			for (int i = size - 1; i >= 0; i--) {
				if (i != (size - 1))
					gate.get(i).setOutputBuffer(gate.get(i + 1).getInputBuffer());
				gate.get(i).reversePropagate();
				;
			}
		} catch (GateException exp) {
			reset();
			exp.printStackTrace();
		}
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder("");
		for (MCTGate mctGate : gate) {
			GateControl[] gC = mctGate.getControl();
			int[] intC = Arrays.stream(gC).mapToInt(GateControl::gateControlToInt).toArray();
			str.append(Arrays.toString(intC) + "\n");
		}
		return str.toString();
	}
}
