package faultmodel.mgf.smgfdatastructure;

/**
 * Thrown for any exception occurring at some of the methods of the class
 * {@link SMGFResult}
 * 
 * @author AKASH
 *
 */
public class SMGFException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Public constructor which takes a error message as a parameter.
	 * 
	 * @param message of type {@link java.lang.String String}
	 */
	public SMGFException(String message) {
		super(message);
	}
}
