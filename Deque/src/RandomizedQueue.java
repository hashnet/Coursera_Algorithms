import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item[] items;
	
	private int size;
	
	
	@SuppressWarnings("unchecked")
	public RandomizedQueue() {                  // construct an empty deque
		items = (Item[]) new Object[1];
		
		size = 0;
	} 
	
	public boolean isEmpty() {                 // is the deque empty?
		return (size == 0);
	} 
	
	public int size() {                        // return the number of items on the deque
		return size;
	} 
	
	public void enqueue(Item item) {           // add the item
		if(item == null) throw new NullPointerException();
		
		if(size >= items.length) resize(2 * items.length);
		
		items[size++] = item;
	}
	
	private void resize(int capacity) {
		@SuppressWarnings("unchecked")
		Item[] temp = (Item[]) new Object[capacity];
		
		for(int i=0; i<size; i++) {
			temp[i] = items[i];
		}
		
		items = temp;
	}
	
	public Item dequeue() {                    // remove and return a random item
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		
		int randIndex = StdRandom.uniform(size);
		
		Item item = items[randIndex];		
		items[randIndex] = items[--size];
		
		if(size > 0 && size <= items.length/4) resize(items.length/2);
		
		return item;
	}
	
	public Item sample() {                     // return (but do not remove) a random item
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		
		int randIndex = StdRandom.uniform(size);
		
		return items[randIndex];
	}
	
	public Iterator<Item> iterator() {         // return an independent iterator over items in random order
		return new RandomIterator();
	}
	
	private class RandomIterator implements Iterator<Item> {
		Item[] temp;
		int currentIndex;
		
		@SuppressWarnings("unchecked")
		public RandomIterator() {
			int[] randIndices = new int[size];
			for(int i=0; i<size; i++) {
				randIndices[i] = i;
			}
			StdRandom.shuffle(randIndices);
			temp  = (Item[]) new Object[size];
			for(int i=0; i<size; i++) {
				temp[i] = items[randIndices[i]];
			}
			
			currentIndex = 0;
		}
		
		@Override
		public boolean hasNext() {
			return (currentIndex < size);
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
			
			return temp[currentIndex++];
		}
		
	}
	
	public static void main(String[] args) {   // unit testing (optional)
	}
}