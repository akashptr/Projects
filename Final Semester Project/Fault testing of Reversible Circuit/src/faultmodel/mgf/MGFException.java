package faultmodel.mgf;

/**
 * Thrown for any possible exception occurring in the methods of the class
 * {@link MGF}
 * 
 * @author AKASH
 *
 */
public class MGFException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Public constructor which takes a error message as a parameter.
	 * 
	 * @param message of type {@link java.lang.String String}
	 */
	public MGFException(String message) {
		super(message);
	}
}
