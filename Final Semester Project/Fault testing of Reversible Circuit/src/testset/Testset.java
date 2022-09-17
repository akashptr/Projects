package testset;

import java.util.ArrayList;
import java.util.Arrays;
import bit.*;

/**
 * Represents a test set which is a collection of test vectors.
 * 
 * @author AKASH
 *
 */
public class Testset {
	private ArrayList<boolean[]> testVector;
	private int order;

	/**
	 * Takes a boolean array and initialize a test set. Also set the order of the
	 * test set.
	 * 
	 * @param vector boolean array representing the test vector.
	 * @throws TestsetException thrown if the argument is null or is an empty array
	 */
	public Testset(boolean[] vector) throws TestsetException {
		if (vector == null || vector.length == 0)
			throw new TestsetException("A testset obj must have atleast one test vector");
		testVector = new ArrayList<>();
		testVector.add(vector);
		order = vector.length;
	}

	/**
	 * Returns the test vector provided by a valid index number.
	 * 
	 * @param index position of the test vector in the test set (valid index from 0
	 *              to number_of_tests - 1).
	 * @return the test vector as boolean array.
	 * @throws TestsetException thrown if the index is out of range
	 */
	public boolean[] getTestVector(int index) throws TestsetException {
		if (index < 0 || index >= testVector.size())
			throw new TestsetException("Index out of bound");
		return testVector.get(index);
	}

	/**
	 * Returns the number of input lines of the test set.
	 * 
	 * @return the number of input lines in the test set.
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * Returns the number of test vectors in the test set.
	 * 
	 * @return the number of test vectors in the test set.
	 */
	public int size() {
		return testVector.size();
	}

	/**
	 * Append a new test vector in the test set.
	 * 
	 * @param vector boolean array representing the test vector to be appended.
	 * @throws TestsetException thrown if the argument is null or the order of the
	 *                          new test vector is not equal to the order of the
	 *                          test set.
	 */
	public void add(boolean[] vector) throws TestsetException {
		if (vector == null || vector.length != order) {
			throw new TestsetException("Incompatible test vector");
		}
		testVector.add(vector);
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder("");
		int[] tVecInt;
		for (boolean[] tVec : testVector) {
			try {
				tVecInt = BitTransform.boolArrToIntArr(tVec);
				str.append(Arrays.toString(tVecInt) + "\n");
			} catch (BitTransformException e) {
				e.printStackTrace();
			}
		}
		return str.toString();
	}
}
