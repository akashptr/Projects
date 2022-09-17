package bit;

/**
 * BitTransform class contains static methods to transform one or an array of
 * integer number(s) to boolean value(s) and vice-versa
 * 
 * @author AKASH
 *
 */
public class BitTransform {
	/**
	 * Converts an int data to boolean. Returns true if bit >= 1 or returns false.
	 * 
	 * @param bit representing one binary digit.
	 * @return true if >=1 or false
	 */
	public static boolean intToBool(int bit) {
		return (bit >= 1);
	}

	/**
	 * Converts boolean to integer
	 * 
	 * @param bit One binary digit
	 * @return 1 if true otherwise 0
	 */
	public static int boolToInt(boolean bit) {
		return bit ? 1 : 0;
	}

	/**
	 * Converts a boolean array to its equivalent integer array.
	 * 
	 * @param bits boolean array
	 * @return a new array of integer
	 * @throws BitTransformException when empty array or null passed as argument.
	 */
	public static int[] boolArrToIntArr(boolean[] bits) throws BitTransformException {
		if (bits == null || bits.length == 0)
			throw new BitTransformException("No array provide");
		int[] intArr = new int[bits.length];
		for (int i = 0; i < bits.length; i++) {
			intArr[i] = boolToInt(bits[i]);
		}
		return intArr;
	}

	/**
	 * Converts an integer array to its equivalent boolean array.
	 * 
	 * @param intArr integer array
	 * @return a new boolean array
	 * @throws BitTransformException when empty array or null passed as argument.
	 */
	public static boolean[] intArrToBoolArr(int[] intArr) throws BitTransformException {
		if (intArr == null || intArr.length == 0)
			throw new BitTransformException("No array provide");
		boolean[] boolArr = new boolean[intArr.length];
		for (int i = 0; i < intArr.length; i++) {
			boolArr[i] = intToBool(intArr[i]);
		}
		return boolArr;
	}
}
