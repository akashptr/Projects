package faultmodel.mgf.smgfdatastructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Data structure for SMGF test results.
 * 
 * @author AKASH
 *
 */
public class SMGFResult {
	private boolean[][] resultset;
	private final int tVCount, gateCount;

	/**
	 * Provided by number of tests and number of gates, it initializes the data
	 * structure to store the test result.
	 * <p>
	 * Both the arguments should be valid(i.e. less than or equal to 0 not allowed)
	 * and must match with actual circuit and test set.
	 * 
	 * @param noOfTestVectors number of test vectors present in the test set.
	 * @param noOfGates       number of gates present in the circuit.
	 * @throws SMGFException thrown if the arguments are negative.
	 */
	public SMGFResult(int noOfTestVectors, int noOfGates) throws SMGFException {
		if (noOfTestVectors <= 0 || noOfGates <= 0)
			throw new SMGFException("Atleast one gate and one test vector is requierd");
		resultset = new boolean[noOfTestVectors][noOfGates];
		tVCount = noOfTestVectors;
		gateCount = noOfGates;
	}

	/**
	 * Updates the result.
	 * <p>
	 * If test vector t tests gate numbers g then call setResult(t, g, true)
	 * otherwise call setResult(t, g, false) (although it is optional).
	 * 
	 * @param testVectorNo index number of test vector(starting index is 0)
	 * @param gateNo       gate number(starting gate number is 0)
	 * @param value        whether it is tested or not.
	 * @throws SMGFException thrown if invalid test vector number or invalid gate
	 *                       number is given
	 */
	public void setResult(int testVectorNo, int gateNo, boolean value) throws SMGFException {
		if (testVectorNo < 0 || testVectorNo >= tVCount)
			throw new SMGFException("Invalid test vector index");
		if (gateNo < 0 || gateNo >= gateCount)
			throw new SMGFException("Invalid gate index");
		resultset[testVectorNo][gateNo] = value;
	}

	/**
	 * Clears the result.
	 */
	public void reset() {
		for (boolean[] tVec : resultset)
			Arrays.fill(tVec, false);
	}

	/**
	 * Returns all the test vectors which test the gate, provided as argument.
	 *
	 * @param gateNo index number of the gate present in the circuit(first is 0).
	 * @return array of test vector numbers which test the gate.
	 * @throws SMGFException thrown if invalid gate number is given
	 */
	public int[] gateToTVecs(int gateNo) throws SMGFException {
		if (gateNo < 0 || gateNo >= gateCount)
			throw new SMGFException("Invalid gate index");
		List<Integer> result = new ArrayList<>();
		for (int t = 0; t < tVCount; t++) {
			if (resultset[t][gateNo])
				result.add(t);
		}
		return result.stream().mapToInt(Integer::intValue).toArray();
	}

	/**
	 * Returns the gates which are tested by the test vector, provided as argument.
	 * 
	 * @param tVecNo index number of the test vector in the test set.
	 * @return array of gate numbers which are tested by the test vector.
	 * @throws SMGFException thrown if invalid test vector number is given.
	 */
	public int[] tVecToGates(int tVecNo) throws SMGFException {
		if (tVecNo < 0 || tVecNo >= tVCount)
			throw new SMGFException("Invalid test vector index");
		List<Integer> result = new ArrayList<>();
		for (int g = 0; g < gateCount; g++) {
			if (resultset[tVecNo][g])
				result.add(g);
		}
		return result.stream().mapToInt(Integer::intValue).toArray();
	}

	/**
	 * Returns true if the entire circuit is tested.
	 * <p>
	 * true if each and every gate of the circuit is tested by at least one test
	 * vector. Otherwise false.
	 * 
	 * @return true if the circuit is tested otherwise false.
	 */
	public boolean isCircuitTested() {
		boolean[] gateResult = new boolean[gateCount];
		for (int t = 0; t < tVCount; t++) {
			for (int g = 0; g < gateCount; g++) {
				gateResult[g] |= resultset[t][g];
			}
		}
		boolean result = true;
		for (int g = 0; g < gateCount; g++) {
			result &= gateResult[g];
		}
		return result;
	}
}
