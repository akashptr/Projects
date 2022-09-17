package reversiblegate.mctgate;
/**
 * Thrown for any exception occurred in {@link MCTGate} class method
 * @author AKASH
 *
 */
public class GateException extends Exception {

	private static final long serialVersionUID = 1L;
	/**
	 * Public constructor which takes a error message as a parameter.
	 * 
	 * @param message of type {@link java.lang.String String}
	 */
	public GateException(String message) {
		super(message);
	}
}
