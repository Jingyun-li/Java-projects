package listClass;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class implements a basic linked list which implements the Iterable
 * interface.
 * 
 * @author Jingyun Li
 * 
 */
public class BasicLinkedList<T> implements Iterable<T> {
	/**
	 * This is a static nested class that implements a linked list node.
	 */
	private static class Node<T> {
		private T data; // A private instance variable that represent the data in the node
		private Node<T> next; // A private instance variable that represent the next node in the linked list

		/**
		 * A private constructor of the Node class. It initiates the inner variables of
		 * the node.
		 * 
		 * @param data
		 */
		private Node(T data) {
			this.data = data;
			next = null;
		}
	}

	private Node<T> head; // A head references for the linked list
	private Node<T> tail; // A tail references for the linked list
	private int size; // A instance variable that keeps track of the current size of the list

	/**
	 * The constructor of the BasicLinkedList class which initiates all the instance
	 * variables.
	 */
	public BasicLinkedList() {
		head = null;
		tail = null;
		size = 0;
	}

	/**
	 * A getter for the current size of the list.
	 * 
	 * @return size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Adds the element to the tail of the list.
	 * 
	 * @param data
	 * @return reference to the current object.
	 */
	public BasicLinkedList<T> addToEnd(T data) {
		Node<T> n = new Node<T>(data);
		size++;
		if (head == null) {
			head = n;
			tail = n;
		} else {
			tail.next = n;
			tail = n;
		}
		return this;
	}

	/**
	 * Adds the element to the front of the list.
	 * 
	 * @param data
	 * @return reference to the current object.
	 */
	public BasicLinkedList<T> addToFront(T data) {
		Node<T> n = new Node<T>(data);
		size++;
		if (head == null) {
			head = n;
			tail = n;
		} else {
			n.next = head;
			head = n;
		}
		return this;
	}

	/**
	 * A getter method for the head element
	 * 
	 * @return the head element, else return null if the list is empty.
	 * 
	 */
	public T getFirst() {
		if (head == null) {
			return null;
		}
		return head.data;
	}

	/**
	 * A getter method for the tail element.
	 * 
	 * @return the tail element, else return null if the list is empty.
	 * 
	 */
	public T getLast() {
		if (head == null) {
			return null;
		}
		return tail.data;
	}

	/**
	 * A method that removes the head element.
	 * 
	 * @return the head element.
	 */
	public T retrieveFirstElement() {
		if (head == null) {
			return null;
		}
		Node<T> remove = head;
		head = head.next;
		if (head == null) {
			tail = null;
		}
		size--;
		return remove.data;
	}

	/**
	 * A method that removes the tail element.
	 * 
	 * @return the tail element.
	 */
	public T retrieveLastElement() {
		if (head == null) {
			return null;
		}
		Node<T> remove = tail;
		Node<T> tmp = null;
		for (Node<T> curr = head; curr != tail; curr = curr.next) {
			tmp = curr;
		}
		tail = tmp;
		if (tail == null) {
			head = null;
		} else {
			tail.next = null;
		}
		size--;
		return remove.data;
	}

	/**
	 * A method that removes all instances of the target element from the list
	 * 
	 * @param targetData
	 * @return
	 */
	public BasicLinkedList<T> removeAllInstances(T targetData) {
		Node<T> prev = null;
		for (Node<T> curr = head; curr != null; curr = curr.next) {
			if (curr.data.equals(targetData)) {
				if (head == curr) {
					head = curr.next;
				} else {
					prev.next = curr.next;
				}
				if (tail == curr) {
					tail = prev;
				}
				size--;
			} else {
				prev = curr;
			}
		}
		return this;
	}

	/**
	 * The method returns an instance of an anonymous inner class that defines an
	 * Iterator over this list.
	 */
	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {

			private Node<T> n = head;

			@Override
			public boolean hasNext() {
				return n != null;
			}

			@Override
			public T next() {
				if (n == null) {
					throw new NoSuchElementException();
				}
				Node<T> tmp = n;
				n = n.next;
				return tmp.data;
			}
		};
	}
}
