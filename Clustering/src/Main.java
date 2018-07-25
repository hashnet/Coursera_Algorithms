import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Main {
	private static class UF<T> {
		private Map<T, T> roots;
		private Map<T, Integer> sizes;
		
		public UF(Iterable<T> nodes) {
			roots = new HashMap<>();
			sizes = new HashMap<>();
			
			Iterator<T> it = nodes.iterator();
			while(it.hasNext()) {
				T node = it.next();
				roots.put(node, node);
				
				sizes.put(node, 1);
			}
		}
		
		public boolean connected(T a, T b) {
			return (root(a) == root(b));
		}
		
		public T root(T node) {
			while(node != roots.get(node)) node = roots.get(node);
			
			return node;
		}
		
		public void union(T a, T b) {
			if(connected(a, b)) return;
			
			T rA = root(a);
			T rB = root(b);
			
			int sA = sizes.get(rA);
			int sB = sizes.get(rB);
			
			if(sA >= sB) {
				roots.put(rB, rA);
				sizes.put(rA, sA+sB);
			} else {
				roots.put(rA, rB);
				sizes.put(rB, sA+sB);
			}
		}
	}
	
	private static class Node {
		private int id;
		
		public Node(int id) {
			this.id = id;
		}
		
		@Override
		public String toString() {
			return "{"+ String.format("%3d", id) +"}";
		}
	}
	
	private static class Edge implements Comparable<Edge>{
		private Node a;
		private Node b;
		private int dist;
		
		public Edge(Node a, Node b, int dist) {
			this.a = a;
			this.b = b;
			this.dist = dist;
		}
		
		@Override
		public String toString() {
			return a + "---" + String.format("%5d", dist) + "---" + b;
		}

		@Override
		public int compareTo(Edge other) {
			return this.dist - other.dist;
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc = new Scanner(new FileInputStream("files/input.txt"));
		int n = sc.nextInt();
		List<Node> nodes = new ArrayList<>();
		for(int i=1; i<=n; i++) {
			nodes.add(new Node(i));
		}
		UF<Node> uf = new UF<>(nodes);
		
		List<Edge> edges = new ArrayList<>();
		while(sc.hasNext()) {
			Node a = nodes.get(sc.nextInt()-1);
			Node b = nodes.get(sc.nextInt()-1);
			int dist = sc.nextInt();
			edges.add(new Edge(a, b, dist));
		}
		
		Collections.sort(edges);
		
		int maxDist = doCluster(edges, uf, n, 4);
		
		System.out.println(maxDist);
		sc.close();
	}
	
	private static int doCluster(List<Edge> edges, UF<Node> uf, int initClusCount, int targetClusCount) {
		int index = 0;
		while(initClusCount > targetClusCount) {
			Edge e = edges.get(index);
			Node a = e.a;
			Node b = e.b;
			if(!uf.connected(a, b)) {
				uf.union(a, b);
				--initClusCount;
			}
			
			++index;
		}
		
		while(true) {
			Edge e = edges.get(index);
			if(!uf.connected(e.a, e.b)) return e.dist;
			
			++index;
		}
	}
}
