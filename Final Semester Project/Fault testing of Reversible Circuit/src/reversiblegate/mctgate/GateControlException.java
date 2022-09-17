package reversiblegate.mctgate;
/**
 * It represents any exception occurred in the methods of the class {@link GateControl}
 * @author AKASH
 *
 */
public class GateControlException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Public constructor which takes a error message as a parameter.
	 * 
	 * @param message of type {@link java.lang.String String}
	 */
	public GateControlException(String message) {
		super(message);
	}
}
