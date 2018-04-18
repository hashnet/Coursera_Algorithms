import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class SolveMultiple {
	public static void main(String[] args) {// solve a slider puzzle (given below)
		// create initial board from file
		for(String fileName : args) {
			In in = new In(fileName);
			StdOut.println("Solution for: " + fileName);
			
			int n = in.readInt();
			int[][] blocks = new int[n][n];
			for (int i = 0; i < n; i++)
			    for (int j = 0; j < n; j++)
			        blocks[i][j] = in.readInt();
			Board initial = new Board(blocks);
		
			// solve the puzzle
			Solver solver = new Solver(initial);
		
			// print solution to standard output
			if (!solver.isSolvable())
			    StdOut.println("No solution possible");
			else {
			    StdOut.println("Minimum number of moves = " + solver.moves());
//			    for (Board board : solver.solution())
//			        StdOut.println(board);
			}
		}
	}
}
