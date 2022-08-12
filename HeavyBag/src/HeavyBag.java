import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;

/**
 * <P>
 * The HeavyBag class implements a Set-like collection that allows duplicates (a
 * lot of them).
 * </P>
 * <P>
 * The HeavyBag class provides Bag semantics: it represents a collection with
 * duplicates. The "Heavy" part of the class name comes from the fact that the
 * class needs to efficiently handle the case where the bag contains 100,000,000
 * copies of a particular item (e.g., don't store 100,000,000 references to the
 * item).
 * </P>
 * <P>
 * In a Bag, removing an item removes a single instance of the item. For
 * example, a Bag b could contain additional instances of the String "a" even
 * after calling b.remove("a").
 * </P>
 * <P>
 * The iterator for a heavy bag must iterate over all instances, including
 * duplicates. In other words, if a bag contains 5 instances of the String "a",
 * an iterator will generate the String "a" 5 times.
 * </P>
 * <P>
 * In addition to the methods defined in the Collection interface, the HeavyBag
 * class supports several additional methods: uniqueElements, getCount, and
 * choose.
 * </P>
 * <P>
 * The class extends AbstractCollection in order to get implementations of
 * addAll, removeAll, retainAll and containsAll. (We will not be over-riding
 * those). All other methods defined in the Collection interface will be
 * implemented here.
 * </P>
 */
public class HeavyBag<T> extends AbstractCollection<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/* Create whatever instance variables you think are good choices */
	/* Use Map as the data structure for HeavyBag */
	private Map<T, Integer> dataMap;

	/**
	 * Initialize a new, empty HeavyBag
	 */
	public HeavyBag() {
		dataMap = new TreeMap<T, Integer>();
	}

	/**
	 * Adds an instance of o to the Bag
	 * 
	 * @return always returns true, since added an element to a bag always changes
	 *         it
	 * 
	 */
	@Override
	public boolean add(T o) {
		Integer count = dataMap.get(o);
		if (count == null) {
			count = 0;
		}
		dataMap.put(o, count + 1);
		return true;
	}

	/**
	 * Adds multiple instances of o to the Bag. If count is less than 0 or count is
	 * greater than 1 billion, throws an IllegalArgumentException.
	 * 
	 * @param o
	 *            the element to add
	 * @param count
	 *            the number of instances of o to add
	 * @return true, since addMany always modifies the HeavyBag.
	 */
	public boolean addMany(T o, int count) {
		if (count < 1 || count > 1000000000) {
			throw new IllegalArgumentException();
		}
		Integer num = dataMap.get(o);
		if (num == null) {
			num = 0;
		}
		dataMap.put(o, num + count);
		return true;
	}

	/**
	 * Generate a String representation of the HeavyBag.
	 * 
	 * @return String
	 */
	@Override
	public String toString() {
		Set<T> keys = dataMap.keySet();
		String sumStr = "";
		for (T k : keys) {
			String str = "[" + k.toString() + "," + dataMap.get(k) + "] ";
			sumStr += str;
		}
		return sumStr;
	}

	/**
	 * Tests if two HeavyBags are equal. Two HeavyBags are considered equal if they
	 * contain the same number of copies of the same elements. Comparing a HeavyBag
	 * to an instance of any other class should return false;
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof HeavyBag)) {
			return false;
		}
		HeavyBag<T> other = (HeavyBag<T>) o;
		if (other.dataMap.size() != dataMap.size()) {
			return false;
		}
		Set<T> keys = dataMap.keySet();
		for (T k : keys) {
			Integer myCount = dataMap.get(k);
			Integer otherCount = other.dataMap.get(k);
			if (!myCount.equals(otherCount)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Return a hashCode of the string of the HeavyBag that returns
	 */
	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	/**
	 * <P>
	 * Returns an iterator over the elements in a heavy bag.
	 */
	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			private int currCount = 0;
			private T currKey = null;
			private Iterator<T> iter = dataMap.keySet().iterator();
			private int currIdx = 0;
			private boolean removeFlag = true;

			@Override
			public boolean hasNext() {
				if (currIdx < currCount - 1) {
					return true;
				}
				return iter.hasNext();
			}

			@Override
			public T next() {
				if (currKey != null) {
					if (++currIdx < currCount) {
						return currKey;
					}
				}
				if (!iter.hasNext()) {
					throw new NoSuchElementException();
				}
				currKey = iter.next();
				currCount = dataMap.get(currKey);
				currIdx = 0;
				return currKey;
			}

			@Override
			public void remove() {
				if (removeFlag || currIdx < 0) {
					throw new IllegalStateException();
				}

				if (currCount > 1) {
					currIdx--;
					currCount--;
					dataMap.put(currKey, currCount);
				} else {
					removeFlag = true;
					iter.remove();
				}
			}

		};
	}

	/**
	 * return a Set of the elements in the Bag (since the returned value is a set,
	 * it can contain no duplicates. It will contain one value for each UNIQUE value
	 * in the Bag).
	 * 
	 * @return A set of elements in the Bag
	 */
	public Set<T> uniqueElements() {
		Set<T> key = dataMap.keySet();
		return key;
	}

	/**
	 * Return the number of instances of a particular object in the bag. Return 0 if
	 * it doesn't exist at all.
	 * 
	 * @param o
	 *            object of interest
	 * @return number of times that object occurs in the Bag
	 */
	public int getCount(Object o) {
		Integer count = dataMap.get(o);
		if (count == null) {
			return 0;
		}
		return count;
	}

	/**
	 * Given a random number generator, randomly choose an element from the Bag
	 * according to the distribution of objects in the Bag (e.g., if a Bag contains
	 * 7 a's and 3 b's, then 70% of the time choose should return an a, and 30% of
	 * the time it should return a b.
	 * 
	 * @param r
	 *            Random number generator
	 * @return randomly chosen element
	 */
	public T choose(Random r) {
		int size = this.size();
		int randomNum = r.nextInt(size);
		int count = 0;
		Set<T> key = dataMap.keySet();
		for (T k : key) {
			int curr = dataMap.get(k);
			if (count <= randomNum && randomNum < count + curr) {
				return k;
			}
			count += curr;
		}
		return null;
	}

	/**
	 * Returns true if the Bag contains one or more instances of o
	 */
	@Override
	public boolean contains(Object o) {
		return dataMap.containsKey(o);
	}

	/**
	 * Decrements the number of instances of o in the Bag.
	 * 
	 * @return return true if and only if at least one instance of o exists in the
	 *         Bag and was removed.
	 */
	@Override
	public boolean remove(Object o) {
		Integer count = dataMap.get(o);
		if (count == null) {
			return false;
		}
		if (count == 1) {
			dataMap.remove(o);

		} else {
			dataMap.put((T) o, count - 1);
		}
		return true;
	}

	/**
	 * Total number of instances of any object in the Bag (counting duplicates)
	 */
	@Override
	public int size() {
		int total = 0;
		Set<T> key = dataMap.keySet();
		for (T k : key) {
			total += dataMap.get(k);
		}
		return total;
	}
}