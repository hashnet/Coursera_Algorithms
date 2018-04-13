import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
   public static void main(String[] args) {
	   if(args.length != 1) throw new IllegalArgumentException();
	   
	   int k = Integer.parseInt(args[0]);
	   
	   RandomizedQueue<String> rQueue = new RandomizedQueue<>();
	 
	   while(!StdIn.isEmpty()) {
		   rQueue.enqueue(StdIn.readString());
	   }
	   
	   while(--k >= 0) {
		   StdOut.println(rQueue.dequeue());
	   }
   }
}