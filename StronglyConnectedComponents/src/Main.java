import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc = new Scanner(new FileInputStream("files/SCC.txt"));
		
		Graph g = new Graph(sc);
		
		g.populate();
		
		//g.print();
		
		g.scc();
		//g.print();
		System.out.println("SCC: ");
		System.out.println(g.getResult());
		
		
	}
}
