package searchTree;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a non-empty search tree. An instance of this class
 * should contain:
 * <ul>
 * <li>A key
 * <li>A value (that the key maps to)
 * <li>A reference to a left Tree that contains key:value pairs such that the
 * keys in the left Tree are less than the key stored in this tree node.
 * <li>A reference to a right Tree that contains key:value pairs such that the
 * keys in the right Tree are greater than the key stored in this tree node.
 * </ul>
 * 
 */
public class NonEmptyTree<K extends Comparable<K>, V> implements Tree<K, V> {

	private Tree<K, V> left, right;
	private K key;
	private V value;

	public NonEmptyTree(K key, V value, Tree<K, V> left, Tree<K, V> right) {
		this.key = key;
		this.value = value;
		this.left = left;
		this.right = right;
	}

	/**
	 * Find the value that this key is bound to in this tree.
	 * 
	 * @param key
	 * @return value associated with the key by this Tree, or null if the key does
	 *         not have an association in this tree.
	 */
	public V search(K key) {
		int answer = key.compareTo(this.key);
		if (answer == 0) {
			return this.value;
		} else if (answer < 0) {
			return this.left.search(key);
		} else {
			return this.right.search(key);
		}
	}

	/**
	 * Insert/update the key with a new value 
	 * 
	 * @param key
	 * @param value
	 * @return updated tree
	 */
	public NonEmptyTree<K, V> insert(K key, V value) {
		int answer = key.compareTo(this.key);
		if (answer == 0) {
			this.value = value;
		} else if (answer < 0) {
			left = this.left.insert(key, value);
		} else {
			right = this.right.insert(key, value);
		}
		return this;
	}
	
	/**
	 * Delete values corresponding to the key given 
	 * @param key 
	 * @return updated tree
	 */
	public Tree<K, V> delete(K key) {
		int answer = key.compareTo(this.key);
		if (answer == 0) {
			try {
				this.key = left.max();
				value = left.search(this.key);
				left = left.delete(this.key);
			} catch (TreeIsEmptyException e) {
				return right;
			}
		} else if (answer < 0) {
			left = left.delete(key);
		} else {
			right = right.delete(key);
		}

		return this;
	}
	
	/**
	 * Return the maximum key
	 * 
	 * @return maximum key
	 * @throws TreeIsEmptyException if the tree is empty
	 */
	public K max() {
		try {
			return this.right.max();
		} catch (TreeIsEmptyException e) {
			return key;
		}
	}

	/**
	 * Returns the minimum key
	 * 
	 * @return minimum key
	 * @throws TreeIsEmptyException if the tree is empty
	 */
	public K min() {
		try {
			return this.left.min();
		} catch (TreeIsEmptyException e) {
			return key;
		}
	}

	/**
	 * @return number of keys that are bound in this tree.
	 */
	public int size() {
		return this.right.size() + this.left.size() + 1;
	}

	/**
	 * Add all keys bound in this tree to the collection c. The elements must be
	 * added in their sorted order.
	 * 
	 * @param c -- a collection of keys
	 */
	public void addKeysToCollection(Collection<K> c) {
		c.add(key);
		this.left.addKeysToCollection(c);
		this.right.addKeysToCollection(c);
	}

	/**
	 * Returns a Tree containing all entries between fromKey and toKey
	 * 
	 * @param fromKey -- Lower bound value for keys in subtree
	 * @param toKey -- Upper bound value for keys in subtree
	 * 
	 * @return Tree containing all entries between fromKey and toKey
	 */
	public Tree<K, V> subTree(K fromKey, K toKey) {
		if (key.compareTo(fromKey) < 0) {
			return right.subTree(fromKey, toKey);
		} else if (key.compareTo(toKey) > 0) {
			return left.subTree(fromKey, toKey);
		} else {
			return new NonEmptyTree<K, V>(key, value, left.subTree(fromKey, toKey), right.subTree(fromKey, toKey));
		}
	}

}