import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.MinPQ;

public class Solver {
	private final Board initial;
	private final Board twin;
	
	private boolean isSolvable;
	
	private List<Board> solution;
	
	private static enum PFunction {HAM, MAN};
	private static final PFunction pFunction = PFunction.MAN;
	
	private static class Node implements Comparable<Node> {
		private final Board board;
		private final int moves;
		private final int priority;
		private final Node parent;
		
		@Override
		public int compareTo(Node other) {
			return this.priority - other.priority;
		}
		
		public Node(Board board, Node parent, int moves) {
			this.board = board;
			this.moves = moves;
			this.parent = parent;
		
			switch(Solver.pFunction) {
			case MAN:
				priority = moves + board.manhattan();
				break;
			case HAM:
			default:
				priority = moves + board.hamming();
				break;
			}
		}
	}
	
	public Solver(Board initial) {			// find a solution to the initial board (using the A* algorithm)
		if (initial == null) throw new IllegalArgumentException();
		
		this.initial = initial;
		this.twin = initial.twin();
		
		solve();
	}
	
	private void solve() {
		isSolvable = false;
		
		MinPQ<Node> mainMinPQ = new MinPQ<>();
		mainMinPQ.insert(new Node(initial, null, 0));
		
		MinPQ<Node> twinMinPQ = new MinPQ<>();
		twinMinPQ.insert(new Node(twin, null, 0));
		
		boolean isMain = true;
		MinPQ<Node> minPQ;
		
		while(!(minPQ = (isMain ? mainMinPQ : twinMinPQ)).isEmpty()) {
			Node node = minPQ.delMin();
			Board board = node.board;
			int moves = node.moves;
			
//			StdOut.println(mainOrTwin);
//			StdOut.println("Moves: " + node.moves);
//			StdOut.println("Priority: " + node.priority);
//			StdOut.println("Man: " + board.manhattan());
//			StdOut.println("Ham: " + board.hamming());
//			StdOut.println(board);
			
			if(board.isGoal()) {
				if(isMain) {
					isSolvable = true;
					solution = getSolution(node);
					
					return;
				} else {
					isSolvable = false;
					
					return;
				}
			}
			
			Node child;
			Node parent;
			for(Board neighbor : board.neighbors()) {
				child = node;
				parent = child.parent;
				if(parent != null) parent = parent.parent;

				boolean repeated = false;
				while(parent != null) {
					if(parent.board.equals(child.board)) {
						repeated = true;
						break;
					}
					child = parent;
					parent = child.parent;
					if(parent != null) parent = parent.parent;
				}
				
				if(!repeated) minPQ.insert(new Node(neighbor, node, moves+1));
			}
			
			isMain = !isMain;
		}
	}
	
	private List<Board> getSolution(Node node){
		if(node == null) return new ArrayList<Board>();
		else {
			List<Board> solution = getSolution(node.parent);
			solution.add(node.board);
			
			return solution;
		}
	}
	
	public boolean isSolvable() {			// is the initial board solvable?
		return isSolvable;
	}
	
	public int moves() {					// min number of moves to solve initial board; -1 if unsolvable
		if(isSolvable) return solution.size()-1;
		else return -1;
	}
	
	public Iterable<Board> solution() {		// sequence of boards in a shortest solution; null if unsolvable
		if(isSolvable) return solution;
		else return null;
	}
	
	public static void main(String[] args) {// solve a slider puzzle (given below)

	}
}