import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
	static Vertex[] vertices;
	static List<Edge> edges;
	
	private static class Vertex {
		int id;
		List<Edge> edges;
		Object set;
		
		public Vertex(int id, Object set) {
			this.id = id;
			this.set = set;
			
			edges = new ArrayList<>();
		}
	}
	
	private static class Edge {
		Vertex v1;
		Vertex v2;
		int cost;
		
		public Edge(Vertex v1, Vertex v2, int cost) {
			this.v1 = v1;
			this.v2 = v2;
			this.cost = cost;
			
			v1.edges.add(this);
			v2.edges.add(this);
		}
		
		@Override
		public String toString() {
			return String.format("{%d <-- %d --> %d}", v1.id, cost, v2.id);
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc = new Scanner(new FileInputStream("files/edges.txt"));
		int v = sc.nextInt();
		int e = sc.nextInt();
		
		Object x = new Object();
		Object y = new Object();
		
		vertices = new Vertex[v+1];
		for(int i=1; i<=v; i++) {
			vertices[i] = new Vertex(i, y);
		}
		
		edges = new ArrayList<>();
		for(int i=1; i<=e; i++) {
			edges.add(new Edge(vertices[sc.nextInt()], vertices[sc.nextInt()], sc.nextInt()));
		}
		
		System.out.println(vertices[1].edges);
		System.out.println(edges.size());
		
		edges.sort(new Comparator<Edge>() {
			@Override
			public int compare(Edge e1, Edge e2) {
				return e1.cost - e2.cost;
			}
		});
		System.out.println(edges.get(0));
		
		List<Edge> minSpanTree = new ArrayList<>();
		int cX = 0;
		int cY = v;
		vertices[1].set = x;
		++cX;
		--cY;
		while(cY != 0) {
			for(Edge edge: edges) {
				if(edge.v1.set != edge.v2.set) {
					edge.v1.set = x;
					edge.v2.set = x;
					++cX;
					--cY;
					
					minSpanTree.add(edge);
					break;
				}
			}
		}
		
		long sum = 0;
		for(Edge edge: minSpanTree) {
			sum += edge.cost;
		}
		
		System.out.println(v);
		System.out.println(minSpanTree.size());
		System.out.println(sum);
		sc.close();
	}
}