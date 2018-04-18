import java.util.ArrayList;
import java.util.List;

public class Board {
	private int[][] blocks;
	
	private final int dimension;
	private int hamming;
	private int manhattan;
	
	public Board(int[][] blocks) {			// construct a board from an n-by-n array of blocks
											// (where blocks[i][j] = block in row i, column j)
		this.blocks = new int[blocks.length][blocks[0].length];
		
		for(int i=0; i<this.blocks.length; i++) {
			for(int j=0; j<this.blocks[i].length; j++) {
				this.blocks[i][j] = blocks[i][j];
			}
		}
		
		this.dimension = blocks.length;
		
		this.hamming = -1;
		this.manhattan = -1;
	}
	
	public int dimension() {				// board dimension n
		return dimension;
	}
	
	public int hamming() {					// number of blocks out of place
		
		if(hamming < 0) hamming = calculateHamming();
		
		return hamming;
	}
	
	public int manhattan() {					// sum of Manhattan distances between blocks and goal
		if(manhattan < 0) manhattan = calculateManhattan();
		
		return manhattan;
	}
	
	public boolean isGoal() {					// is this board the goal board?
		return manhattan() == 0 ? true : false;
	}
	
	public Board twin() {						// a board that is obtained by exchanging any pair of blocks
		Board twin = new Board(blocks);
		twin.makeTwin();
		
		return twin;
	}
	
	public boolean equals(Object y) {			// does this board equal y?
		if(!(y instanceof Board)) return false;
		
		Board other = (Board) y;
		if(this.dimension != other.dimension) return false;
		
		if(this.manhattan() != other.manhattan()) return false;
		
		for(int i=0; i<dimension; i++) {
			for(int j=0; j<dimension; j++) {
				if(this.blocks[i][j] != other.blocks[i][j]) return false;
			}
		}
		
		return true;
	}
	
	public Iterable<Board> neighbors() {		// all neighboring boards
		List<Board> boards = new ArrayList<>();
		
		int blankRow = -1;
		int blankCol = -1;
		
		for(int i=0; i<dimension; i++) {
			for(int j=0; j<dimension; j++) {
				if(blocks[i][j] == 0) {
					blankRow = i;
					blankCol = j;
				} else {
					continue;
				}
			}
		}
		
		if(blankRow > 0) {
			boards.add(createNeighbors(blankRow, blankCol, blankRow-1, blankCol));
		}
		if(blankRow < dimension-1) {
			boards.add(createNeighbors(blankRow, blankCol, blankRow+1, blankCol));
		}
		if(blankCol > 0) {
			boards.add(createNeighbors(blankRow, blankCol, blankRow, blankCol-1));
		}
		if(blankCol < dimension-1) {
			boards.add(createNeighbors(blankRow, blankCol, blankRow, blankCol+1));
		}
		
		return boards;
	}
	
	private Board createNeighbors(int blankRow, int blankCol, int swapRow, int swapCol) {
		Board neighbor = new Board(blocks);
		
		neighbor.swap(blankRow, blankCol, swapRow, swapCol);

		int num = blocks[swapRow][swapCol];
		int origRow = (num-1) / dimension;
		int origCol = (num-1) % dimension;
		
		int prevManhattan = Math.abs(origRow-swapRow) + Math.abs(origCol-swapCol);
		int newManhattan = Math.abs(origRow-blankRow) + Math.abs(origCol-blankCol);
		
		neighbor.manhattan = manhattan() + newManhattan - prevManhattan;
		
		return neighbor;
	}
	
	private void swap(int row1, int col1, int row2, int col2) {
		int temp = blocks[row1][col1];
		blocks[row1][col1] = blocks[row2][col2];
		blocks[row2][col2] = temp;
	}
	
	public String toString() {				// string representation of this board (in the output format specified below)
		StringBuilder builder = new StringBuilder();
		
		builder.append(dimension + "\n");
		for(int i=0; i<dimension; i++) {
			for(int j=0; j<dimension; j++) {
				builder.append(String.format("%2d ", blocks[i][j]));
			}
			
			builder.append("\n");
		}
		
		return builder.toString();
	}
	
	public static void main(String[] args) {// unit tests (not graded)
	}
	
	private int calculateHamming() {
		int sum = 0;
		for(int i=0; i<dimension; i++) {
			for(int j=0; j<dimension; j++) {
				int num = blocks[i][j];
				if(num == 0) continue;
				
				int origRow = (num-1) / dimension;
				int origCol = (num-1) % dimension;
				
				if(!(i == origRow && j == origCol)) ++ sum;
			}
		}
		
		return sum;
	}
	
	private int calculateManhattan() {
		int sum = 0;
		for(int i=0; i<dimension; i++) {
			for(int j=0; j<dimension; j++) {
				int num = blocks[i][j];
				if(num == 0) continue;
				
				int origRow = (num-1) / dimension;
				int origCol = (num-1) % dimension;
				
				sum += Math.abs(i - origRow);
				sum += Math.abs(j - origCol);
			}
		}
		
		return sum;
	}
	
	private void makeTwin() {
		boolean replaced = false;
		for(int i=0; i<dimension; i++) {
			for(int j=0; j<dimension; j++) {
				if(blocks[i][j] != 0) {
					if(j+1 < dimension && blocks[i][j+1] != 0) {
						swap(i, j, i, j+1);
						
						replaced = true;
						break;
					} 
					
					if(i+1 < dimension && blocks[i+1][j] != 0) {
						swap(i, j, i+1, j);
						
						replaced = true;
						break;
					}
				}
			}
			
			if(replaced) break;
		}
	}
}