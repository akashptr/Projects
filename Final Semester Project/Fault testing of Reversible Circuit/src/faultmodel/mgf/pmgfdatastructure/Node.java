package faultmodel.mgf.pmgfdatastructure;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Represents a node that is one individual element of a graph which is further
 * used by {@link PMGFResult} class to store PMGF test result.
 * 
 * @author AKASH
 *
 */
class Node {
	private Node parent;
	private int data;
	private Map<Integer, Node> child;

	/**
	 * Initialize a node with an integer data also initialize the parent node to
	 * null and child map to an empty map.
	 * 
	 * @param nodeData data of the node.
	 */
	public Node(int nodeData) {
		parent = null;
		data = nodeData;
		child = new HashMap<>();
	}

	/**
	 * Links the argument node with the invoking node.
	 * <p>
	 * Make the argument node child of the invoking node. Similarly makes the
	 * invoking node parent of the argument node.
	 * 
	 * @param key       entry in the child map
	 * @param childNode the actual node
	 */
	void setChildWithParent(int key, Node childNode) {
		if (childNode != null) {
			child.put(key, childNode);
			childNode.parent = this;
		}
	}

	/**
	 * Creates unidirectional link from invoking node to argument node.
	 * <p>
	 * Generally used to link gate controls to test vectors and vice-versa.
	 * 
	 * @param key       entry in the child map
	 * @param childNode actual node
	 */
	void setOnlyChild(int key, Node childNode) {
		if (childNode != null) {
			child.put(key, childNode);
		}
	}

	/**
	 * Returns the child node provided with the key of the child map.
	 * 
	 * @param key of the child map.
	 * @return child node.
	 */
	Node getChild(int key) {
		return child.get(key);
	}

	/**
	 * Returns true if the node has at least one child.
	 * 
	 * @return true if the node have at least one child otherwise false.
	 */
	boolean haveChild() {
		return !child.isEmpty();
	}

	/**
	 * Returns the number of children a node has
	 * 
	 * @return number of children that the node has.
	 */
	int getNoOfChildren() {
		return child.size();
	}

	/**
	 * Returns all the child key of the child map of the node.
	 * 
	 * @return a {@link Set} of {@link Integer}s representing all child keys.
	 */
	Set<Integer> getAllChildKey() {
		return child.keySet();
	}

	/**
	 * Returns the data of the node.
	 * 
	 * @return data of the node.
	 */
	int getData() {
		return data;
	}

	/**
	 * Returns the parent node of the invoking node.
	 * 
	 * @return parent node.
	 */
	Node getParent() {
		return parent;
	}
}
