import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private int n; // dimension of the n-byn grid
	private WeightedQuickUnionUF wquf, wqufTop;
	private int topVirtual;
	private int bottomVirtual;
	private boolean[] open; // status of sties that are open including top and
							// bottom virutal sites
	private int nOpen; // number of open sites

	public Percolation(int n) { // create n-by-n grid, with all sites blocked
		if (n <= 0)
			throw new IllegalArgumentException();

		this.n = n;
		wquf = new WeightedQuickUnionUF((n * n) + 2);
		wqufTop = new WeightedQuickUnionUF((n * n) + 2);

		open = new boolean[(n * n) + 2];
		topVirtual = 0;
		bottomVirtual = (n * n) + 1;
		open[topVirtual] = true; // open the top virtual site
		open[bottomVirtual] = true; // open the bottom virtual site

		nOpen = 0;
	}

	public void open(int row, int col) { // open site (row, col) if it is not
											// open already
		if (row < 1 || row > n || col < 1 || col > n)
			throw new IndexOutOfBoundsException();

		if (isOpen(row, col))
			return;

		if (row == 1) {
			wquf.union(topVirtual, twoDToOneD(row, col));
			wqufTop.union(topVirtual, twoDToOneD(row, col));
		} else if (isOpen(row - 1, col)) {
			wquf.union(twoDToOneD(row - 1, col), twoDToOneD(row, col));
			wqufTop.union(twoDToOneD(row - 1, col), twoDToOneD(row, col));
		}

		if (row == n) {
			wquf.union(bottomVirtual, twoDToOneD(row, col));
		} else if (isOpen(row + 1, col)) {
			wquf.union(twoDToOneD(row + 1, col), twoDToOneD(row, col));
			wqufTop.union(twoDToOneD(row + 1, col), twoDToOneD(row, col));
			
		}

		if (col > 1 && isOpen(row, col - 1)) {
			wquf.union(twoDToOneD(row, col - 1), twoDToOneD(row, col));
			wqufTop.union(twoDToOneD(row, col - 1), twoDToOneD(row, col));
		}

		if (col < n && isOpen(row, col + 1)) {
			wquf.union(twoDToOneD(row, col + 1), twoDToOneD(row, col));
			wqufTop.union(twoDToOneD(row, col + 1), twoDToOneD(row, col));
		}

		open[twoDToOneD(row, col)] = true;
		++nOpen;
	}

	public boolean isOpen(int row, int col) { // is site (row, col) open?
		if (row < 1 || row > n || col < 1 || col > n)
			throw new IndexOutOfBoundsException();

		return open[twoDToOneD(row, col)];
	}

	public boolean isFull(int row, int col) { // is site (row, col) full?
		if (row < 1 || row > n || col < 1 || col > n)
			throw new IndexOutOfBoundsException();

		if (!isOpen(row, col))
			return false;
		else
			return wqufTop.connected(topVirtual, twoDToOneD(row, col));
	}

	public int numberOfOpenSites() { // number of open sites
		return nOpen;
	}

	public boolean percolates() { // does the system percolate?
		return wquf.connected(topVirtual, bottomVirtual);
	}

	public static void main(String[] args) { // test client (optional)
		// StdOut.println(simulate(2000));
	}

	private int twoDToOneD(int row, int col) {
		if (row < 1 || row > n || col < 1 || col > n)
			throw new IndexOutOfBoundsException();

		return ((row - 1) * n) + col;
	}

	/*
	 * private int getRow(int index) { if (index < 1 || index > n * n) throw new
	 * IndexOutOfBoundsException();
	 * 
	 * return ((index - 1) / n) + 1; }
	 */

	/*
	 * private int getCol(int index) { if (index < 1 || index > n * n) throw new
	 * IndexOutOfBoundsException();
	 * 
	 * return ((index - 1) % n) + 1; }
	 */
}