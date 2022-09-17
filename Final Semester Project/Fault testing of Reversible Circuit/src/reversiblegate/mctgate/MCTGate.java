package reversiblegate.mctgate;

import java.util.Arrays;

/**
 * Represent individual MCT gate
 * 
 * @author AKASH
 *
 */
public class MCTGate {

	private boolean[] inputBuffer, outputBuffer;
	private final GateControl[] control;
	private final int order;

	private GateControl[] validate(int[] control) throws GateException {
		if (control == null || control.length == 0)
			throw new GateException("No control provided");
		GateControl[] newControl = new GateControl[control.length];
		int targetCount = 0;
		for (int c = 0; c < control.length; c++) {
			try {
				newControl[c] = GateControl.intToGateControl(control[c]);
			} catch (GateControlException exp) {
				GateException gExp = new GateException("Invalid Control");
				gExp.initCause(exp);
				throw gExp;
			}
			if (newControl[c] == GateControl.TARGET_INPUT)
				targetCount++;
		}
		if (targetCount != 1)
			throw new GateException("Number of target input should be exactly one");
		return newControl;
	}

	/**
	 * Public constructor to initialize a MCT gate.
	 * <p>
	 * It takes an integer array representing gate points. Then validates the
	 * parameter. If the parameter is valid only then it creates the object
	 * otherwise throws {@link GateException}
	 * 
	 * @param control of the first gate
	 * @throws GateException thrown if the validation fails. Validation includes -
	 *                       <ul>
	 *                       <li>null check</li>
	 *                       <li>input line check</li>
	 *                       <li>compatibility check</li>
	 *                       </ul>
	 * 
	 */
	public MCTGate(int[] control) throws GateException {
		this.control = validate(control);
		this.order = this.control.length;
		this.inputBuffer = new boolean[this.order];
		this.outputBuffer = new boolean[this.order];
	}

	/**
	 * Returns the number of input lines of the gate
	 * 
	 * @return the number of input lines of the gate
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * Returns the input buffer of the gate.
	 * <p>
	 * It do not returns the actual buffer but a new array containing the value of
	 * the buffer.
	 * 
	 * @return boolean array representing input buffer
	 */
	public boolean[] getInputBuffer() {
		return inputBuffer.clone();
	}

	/**
	 * Returns the output buffer of the gate.
	 * <p>
	 * It do not returns the actual buffer but a new array containing the value of
	 * the buffer.
	 * 
	 * @return boolean array representing output buffer
	 */
	public boolean[] getOutputBuffer() {
		return outputBuffer.clone();
	}

	/**
	 * Returns a array of {@link GateControl}
	 * <p>
	 * Do not returns the actual array but returns a new copied array.
	 * 
	 * @return GateControl array representing gate points
	 */
	public GateControl[] getControl() {
		return control.clone();
	}

	/**
	 * Accepts a boolean array and loads the data to the input buffer of the gate.
	 * <p>
	 * Do not set the passed array as the input buffer, rather copy the content to
	 * the buffer. Buffer is set only when the parameter is valid, otherwise throws
	 * GateException.
	 * 
	 * @param buffer boolean array from which the values will be copied to the input
	 *               buffer of the gate.
	 * @throws GateException thrown if the argument is null or the order of the gate
	 *                       and the order of the argument mismatch
	 */
	public void setInputBuffer(boolean[] buffer) throws GateException {
		if (buffer == null || buffer.length != order)
			throw new GateException("Incompatible input buffer size");
		System.arraycopy(buffer, 0, inputBuffer, 0, order);
	}

	/**
	 * Accepts a boolean array and loads the data to the output buffer of the gate.
	 * <p>
	 * Do not set the passed array as the output buffer, rather copy the content to
	 * the buffer. Buffer is set only when the parameter is valid, otherwise throws
	 * GateException.
	 * 
	 * @param buffer boolean array from which the values will be copied to the
	 *               output buffer of the gate.
	 * @throws GateException thrown if the argument is null or the order of the gate
	 *                       and the order of the argument mismatch
	 */
	public void setOutputBuffer(boolean[] buffer) throws GateException {
		if (buffer == null || buffer.length != order)
			throw new GateException("Incompatible output buffer size");
		System.arraycopy(buffer, 0, outputBuffer, 0, order);
	}

	/**
	 * Propagates the input through the gate and populates the output buffer with
	 * the output.
	 */
	public void propagate() {
		boolean andedControlInput = true;
		int targetIndex = -1;
		for (int i = 0; i < order; i++) {
			outputBuffer[i] = inputBuffer[i];
			if (control[i] == GateControl.TARGET_INPUT)
				targetIndex = i;
			else if (control[i] == GateControl.CONTROL_INPUT)
				andedControlInput &= inputBuffer[i];
		}
		if (andedControlInput)
			outputBuffer[targetIndex] = (!inputBuffer[targetIndex]);
	}

	/**
	 * Propagate in reverse order i.e. from output to input.
	 * <p>
	 * Compute the input from the output.
	 */
	public void reversePropagate() {
		boolean[] temp = inputBuffer;
		inputBuffer = outputBuffer;
		outputBuffer = temp;
		propagate();
		temp = inputBuffer;
		inputBuffer = outputBuffer;
		outputBuffer = temp;
	}

	/**
	 * Clears the input and output buffer.
	 * <p>
	 * Both the buffers are set to false after this operation.
	 */
	public void clearBuffers() {
		Arrays.fill(inputBuffer, false);
		Arrays.fill(outputBuffer, false);
	}
}
