
public class PercolationTest {
	public static void main(String[] args) {
		Percolation p = new Percolation(3);
		
		p.open(1, 3);
		p.open(2, 3);
		p.open(3, 3);
		p.open(3, 1);
		
		System.out.println(p.isFull(3, 1));
	}
}
