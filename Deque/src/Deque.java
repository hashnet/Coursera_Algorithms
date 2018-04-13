import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
	private class Node {
		Item item;
		Node next;
		Node prev;
	}
	
	private Node first, last;
	private int size;
	
	public Deque() {                           // construct an empty deque
		first = null;
		last = null;
		
		size = 0;
	} 
	
	public boolean isEmpty() {                 // is the deque empty?
		return (size == 0);
	} 
	
	public int size() {                        // return the number of items on the deque
		return size;
	} 
	
	public void addFirst(Item item) {          // add the item to the front
		if(item == null) {
			throw new NullPointerException();
		}
		
		Node oldFirst = first;
		
		first = new Node();
		first.item = item;
		first.prev = null;
		first.next = oldFirst;
		
		if(oldFirst == null) {
			last = first;
		} else {
			oldFirst.prev = first;
		}
	
		++size;
	} 
	
	public void addLast(Item item) {           // add the item to the end
		if(item == null) {
			throw new NullPointerException();
		}
		
		Node oldLast = last;
		
		last = new Node();
		last.item = item;
		last.next = null;
		last.prev = oldLast;
		
		if(oldLast == null) {
			first = last;
		} else {
			oldLast.next = last;
		}
		
		++size;
	} 
	
	public Item removeFirst() {                // remove and return the item from the front
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		
		Item item = first.item;
		
		first = first.next;
		
		if(first == null) {
			last = first;
		} else {
			first.prev = null;
		}
		
		--size;
		
		return item;
	}
	
	public Item removeLast() {                 // remove and return the item from the end
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		
		Item item = last.item;
		
		last = last.prev;
		
		if(last == null) {
			first = last;
		} else {
			last.next = null;
		}
		
		--size;
		
		return item;
	} 
	
	public Iterator<Item> iterator() {         // return an iterator over items in order from front to end
		return new FirstToLastIterator();
	}
	
	private class FirstToLastIterator implements Iterator<Item>{
		private Node current = first;
		
		@Override
		public boolean hasNext() {
			return (current != null);
		}
		
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

		@Override
		public Item next() {
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			
			Item item = current.item;
			
			current = current.next;
			
			return item;
		}		
	}
	
	private class LastToFirstIterator implements Iterator<Item>{
		private Node current = last;
		
		@Override
		public boolean hasNext() {
			return (current != null);
		}
		
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

		@Override
		public Item next() {
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			
			Item item = current.item;
			
			current = current.prev;
			
			return item;
		}		
	}
	
	public static void main(String[] args) {   // unit testing (optional)
	}
}