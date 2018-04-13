import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private int n;
	private int trials;

	private double[] a;

	public PercolationStats(int n, int trials) { // perform trials independent
													// experiments on an n-by-n
													// grid
		if (n <= 0 || trials <= 0)
			throw new IllegalArgumentException();

		this.n = n;
		this.trials = trials;

		a = new double[trials];

		for (int i = 0; i < trials; i++) {
			a[i] = simulate(n);
		}
	}

	public double mean() { // sample mean of percolation threshold
		return StdStats.mean(a);
	}

	public double stddev() { // sample standard deviation of percolation
								// threshold
		return StdStats.stddev(a);
	}

	public double confidenceLo() { // low endpoint of 95% confidence interval
		double m = mean();
		double s = stddev();

		return m - (1.96 * s / (Math.sqrt(trials)));
	}

	public double confidenceHi() { // high endpoint of 95% confidence interval
		double m = mean();
		double s = stddev();

		return m + (1.96 * s / (Math.sqrt(trials)));
	}

	public static void main(String[] args) { // test client (described below)
		if (args.length != 2)
			throw new IllegalArgumentException();

		int n = Integer.parseInt(args[0]);
		int trials = Integer.parseInt(args[1]);

		// int n = 200;
		// int trials = 100;
		PercolationStats pS = new PercolationStats(n, trials);

		StdOut.println("mean                    = " + pS.mean());
		StdOut.println("stddev                  = " + pS.stddev());
		StdOut.print("95% confidence interval = [");
		StdOut.print(pS.confidenceLo());
		StdOut.print(", ");
		StdOut.print(pS.confidenceHi());
		StdOut.println("]");
	}

	private double simulate(int dim) {
		Percolation p = new Percolation(dim);

		int[] openOrder = StdRandom.permutation(dim * dim);

		int count = 0;
		while (count < openOrder.length) {
			p.open(getRow(openOrder[count] + 1), getCol(openOrder[count] + 1));
			++count;

			if (p.percolates())
				break;
		}

		return (double) count / (dim * dim);
	}

	private int getRow(int index) {
		if (index < 1 || index > n * n)
			throw new IndexOutOfBoundsException();

		return ((index - 1) / n) + 1;
	}

	private int getCol(int index) {
		if (index < 1 || index > n * n)
			throw new IndexOutOfBoundsException();

		return ((index - 1) % n) + 1;
	}
}