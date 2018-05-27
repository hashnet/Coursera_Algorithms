import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc = new Scanner(new FileInputStream("files/dijkstraData.txt"));
		
		int max = sc.nextInt();
		sc.nextLine();
		
		Graph g = new Graph(max);
		g.populate(sc);
		g.initializeDijkstra(1);
		g.dijkstra();
		int[] vids = {7,37,59,82,99,115,133,165,188,197};
		System.out.println(g.getMinDists(vids));

	}
}
