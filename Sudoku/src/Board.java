import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Board {
	private static final int DIM = 9;
	private class Node {
		private int val;
		private List<Integer> bucket;
		
		public Node(int val) {
			this.val = val;
			this.bucket = new LinkedList<>();
		}
		
		
	}
	
	private Node[][] nodes = new Node[DIM+1][DIM+1];
	
	public void populateBoared(Scanner sc) {
		for(int i=1; i<=DIM; i++) {
			for(int j=1; j<=DIM; j++) {
				nodes[i][j] = new Node(sc.nextInt());
			}
		}
	}
	
	public boolean populateBuckets() {
		for(int i=1; i<=DIM; i++) {
			for(int j=1; j<=DIM; j++) {
				if(nodes[i][j].val == 0) {
					for(int k=1; k<=DIM; k++) nodes[i][j].bucket.add(k);
					
					if(removeBuckets(i, j) == false) return false;
				}
			}
		}
		
		return true;
	}
	
	private boolean removeBuckets(int row, int col) {
		for(int j=1; j<=DIM; j++) {
			int val = nodes[row][j].val;
			if(val != 0) {
				nodes[row][col].bucket.remove(new Integer(val));
			}
		}
		
		for(int i=1; i<=DIM; i++) {
			int val = nodes[i][col].val;
			if(val != 0) {
				nodes[row][col].bucket.remove(new Integer(val));
			}
		}
		
		int rowBlock = ((row-1)/3) + 1;
		int colBlock = ((col-1)/3) + 1;
		
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				int val = nodes[rowBlock+i][colBlock+j].val;
				nodes[row][col].bucket.remove(new Integer(val));
			}
		}
		
		if(nodes[row][col].bucket.size() == 0) return false;
		else return true;
	}
	
	public void printBoard() {
		for(int i=1; i<=DIM; i++) {
			for(int j=1; j<=DIM; j++) {
				if(nodes[i][j].val != 0) {
					System.out.print(nodes[i][j].val + " ");
				} else {
					System.out.print(nodes[i][j].bucket + " ");
				}
			}
			
			System.out.println();
		}
	}
	
//	public boolean solve() {
//		for(int i=1; i<=DIM; i++) {
//			for(int j=1; j<=DIM; j++) {
//				
//			}
//		}
//	}
}
