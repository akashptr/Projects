package bit;

/**
 * Represents any exception occurred in the methods of the class
 * {@link BitTransform}
 * 
 * @author AKASH
 *
 */
public class BitTransformException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Public constructor which takes a error message as a parameter.
	 * 
	 * @param message of type {@link java.lang.String String}
	 */
	public BitTransformException(String message) {
		super(message);
	}
}
