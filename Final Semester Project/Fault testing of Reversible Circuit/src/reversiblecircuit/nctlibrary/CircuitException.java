package reversiblecircuit.nctlibrary;

/**
 * Thrown for any possible exception occurring in the methods of the class
 * {@link Circuit}
 * 
 * @author AKASH
 *
 */
public class CircuitException extends Exception {

	private static final long serialVersionUID = 1L;
	/**
	 * Public constructor which takes a error message as a parameter.
	 * 
	 * @param message of type {@link java.lang.String String}
	 */
	public CircuitException(String message) {
		super(message);
	}

}
