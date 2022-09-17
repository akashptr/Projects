package reversiblegate.mctgate;

/**
 * It is an enum representing three possible inputs of a MCT gate. It also
 * contains methods to convert integer value to its equivalent enum constants.
 * 
 * @author AKASH
 *
 */
public enum GateControl {
	/**
	 * For a gate point which is not connected to the input line
	 */
	NO_INPUT(0),
	/**
	 * For a gate point which is connected to the input line as a control input
	 */
	CONTROL_INPUT(1),
	/**
	 * For a gate point which is connected to the input line as a target input
	 */
	TARGET_INPUT(2);

	private int intValue;

	private GateControl(int value) {
		intValue = value;
	}

	/**
	 * Returns the integer value of the gate control.
	 * 
	 * @return integer representation of the invoking enum constant.
	 */
	public int getValue() {
		return intValue;
	}

	/**
	 * Converts an integer value to its equivalent gate control
	 * 
	 * @param control of a gate in integer
	 * @return GateControl constant
	 * @throws GateControlException thrown for any argument other than 0, 1 and 2.
	 */
	public static GateControl intToGateControl(int control) throws GateControlException {
		switch (control) {
		case 0:
			return NO_INPUT;
		case 1:
			return CONTROL_INPUT;
		case 2:
			return TARGET_INPUT;
		}
		throw new GateControlException("Invalid control for MCT gate");
	}

	/**
	 * Returns equivalent integer value of a GateControl
	 * 
	 * @param control of type GateControl
	 * @return integer value of the control
	 */
	public static int gateControlToInt(GateControl control) {
		return control.getValue();
	}
}
