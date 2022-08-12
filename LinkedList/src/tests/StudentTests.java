package tests;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import listClass.BasicLinkedList;

public class StudentTests {

	@Test
	public void testNewList() {
		BasicLinkedList<Integer> bll= new BasicLinkedList<Integer>();
		int size = bll.getSize();
		assertTrue(size==0);		
	}

	@Test
	public void testAddToEnd() {
		BasicLinkedList<Integer> bll= new BasicLinkedList<Integer>();
		
		assertEquals(bll.addToEnd(1),bll);
		assertEquals(bll.addToEnd(2),bll);
		assertEquals(bll.addToEnd(3),bll);
		int size = bll.getSize();		
		assertTrue(size==3);
		
		Iterator<Integer> iter = bll.iterator();
		assertTrue(iter.hasNext());
		assertEquals(iter.next(),Integer.valueOf(1));
		assertTrue(iter.hasNext());
		assertEquals(iter.next(),Integer.valueOf(2));
		assertTrue(iter.hasNext());
		assertEquals(iter.next(),Integer.valueOf(3));
		assertFalse(iter.hasNext());
		
	}

	public void testAddToFront() {
		BasicLinkedList<Integer> bll= new BasicLinkedList<Integer>();
		
		assertEquals(bll.addToFront(1),bll);
		assertEquals(bll.addToFront(2),bll);
		assertEquals(bll.addToFront(3),bll);
		int size = bll.getSize();		
		assertTrue(size==3);
		
		Iterator<Integer> iter = bll.iterator();
		assertTrue(iter.hasNext());
		assertEquals(iter.next(),Integer.valueOf(3));
		assertTrue(iter.hasNext());
		assertEquals(iter.next(),Integer.valueOf(2));
		assertTrue(iter.hasNext());
		assertEquals(iter.next(),Integer.valueOf(1));
		assertFalse(iter.hasNext());
		
	}
	
	@Test
	public void testGetters() {
		BasicLinkedList<Integer> bll= new BasicLinkedList<Integer>();
		
		assertEquals(bll.getFirst(),null);
		assertEquals(bll.getLast(),null);
		
		bll.addToEnd(1);
		assertEquals(bll.getFirst(),Integer.valueOf(1));		
		assertEquals(bll.getLast(),Integer.valueOf(1));
		
		bll.addToEnd(2);
		assertEquals(bll.getFirst(),Integer.valueOf(1));		
		assertEquals(bll.getLast(),Integer.valueOf(2));

		bll.addToFront(3);
		assertEquals(bll.getFirst(),Integer.valueOf(3));		
		assertEquals(bll.getLast(),Integer.valueOf(2));

	}

	@Test
	public void testRetrieveFirstElements() {
		BasicLinkedList<Integer> bll= new BasicLinkedList<Integer>();
		bll.addToEnd(1);
		bll.addToEnd(2);
		bll.addToEnd(3);
		
		assertEquals(bll.retrieveFirstElement(),Integer.valueOf(1));
		assertEquals(bll.retrieveFirstElement(),Integer.valueOf(2));
		assertEquals(bll.retrieveFirstElement(),Integer.valueOf(3));
		assertTrue(bll.getSize() == 0);
	}
	
	@Test
	public void testRetrieveLastElements() {
		BasicLinkedList<Integer> bll= new BasicLinkedList<Integer>();
		bll.addToEnd(1);
		bll.addToEnd(2);
		bll.addToEnd(3);
		
		assertEquals(bll.retrieveLastElement(),Integer.valueOf(3));
		assertEquals(bll.retrieveLastElement(),Integer.valueOf(2));
		assertEquals(bll.retrieveLastElement(),Integer.valueOf(1));
		assertTrue(bll.getSize() == 0);
	}
	

	@Test
	public void testRemoveAllInstances() {
		BasicLinkedList<Integer> bll= new BasicLinkedList<Integer>();
		assertEquals(bll.removeAllInstances(1),bll);

		
		bll.addToEnd(1);
		bll.addToEnd(2);
		bll.addToEnd(3);
		bll.addToEnd(1);
		
		bll.removeAllInstances(1);
		assertTrue(bll.getSize() == 2);

		bll.removeAllInstances(1);
		assertTrue(bll.getSize() == 2);
	}
	

}
