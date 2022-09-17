package testset;

/**
 * Thrown for any exception occurred in the methods of the class {@link Testset}
 * 
 * @author AKASH
 *
 */
public class TestsetException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Public constructor which takes a error message as a parameter.
	 * 
	 * @param message of type {@link java.lang.String String}
	 */
	public TestsetException(String message) {
		super(message);
	}
}
