import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc;
		Graph graph;
		
		int min = Integer.MAX_VALUE;	
		for(int i=1; i<=200; i++) {
			sc = new Scanner(new FileInputStream("files/KargerMinCut.txt"));
			graph = new Graph();
			graph.populateGraph(sc);
			
			int x = graph.minCut();
			if(x < min) min = x;
			
			sc.close();
			
			System.out.println(min);
		}
	}
}
