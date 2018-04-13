

public class Tester {
	public static void main(String[] args) {
		System.out.println("Deque:");
		Deque<Integer> deque = new Deque<>();
		
		for(int i=1; i<=10; i++) {
			deque.addLast(i);
		}
		
		for(int i : deque) {
			System.out.print(i + " ");
		}
		System.out.println();
		
		while(!deque.isEmpty()) {
			System.out.print(deque.removeFirst() + " ");
		}
		System.out.println();
				
		
		System.out.println("RandomizedQueue");
		RandomizedQueue<Integer> rQueue = new RandomizedQueue<>();
		
		for(int i=1; i<=10; i++) {
			rQueue.enqueue(i);
		}
		
		for(int i : rQueue) {
			System.out.print(i + " ");
		}
		System.out.println();
		
		for(int i : rQueue) {
			System.out.print(i + " ");
		}
		System.out.println();
		
		while(!rQueue.isEmpty()) {
			System.out.print(rQueue.dequeue() + " ");
		}
		System.out.println();
	}
}
