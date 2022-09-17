package faultmodel.mgf.pmgfdatastructure;

/**
 * Thrown for any exception occurring at some of the methods of the classes
 * {@link Node} and {@link PMGFResult}
 * 
 * @author AKASH
 *
 */
public class PMGFException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Public constructor which takes a error message as a parameter.
	 * 
	 * @param message of type {@link java.lang.String String}
	 */
	public PMGFException(String message) {
		super(message);
	}
}
