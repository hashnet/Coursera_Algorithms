import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Graph {
	private class Vertex {
		private int id;
		private List<Integer> forward;
		private List<Integer> backward;
		private boolean discovered;
		private int finalIndex;
		
		public Vertex(int id) {
			this.id = id;
			this.forward = new ArrayList<>();
			this.backward = new ArrayList<>();
		}
		
		@Override
		public String toString() {
			return "id: " + id + ", forward: " + forward + ", backward: " + backward + ", fi: " + finalIndex;
		}
	}
	
	private int vCount;
	private Vertex[] vertices;
	private Scanner sc;
	public Graph(Scanner sc) {
		this.sc = sc;
	}
	
	public void populate() {
		vCount = sc.nextInt();
		vertices = new Vertex[vCount + 1];
		
		for(int i=1; i<=vCount; i++) {
			vertices[i] = new Vertex(i);
		}
		
		while(sc.hasNext()) {
			int u = sc.nextInt();
			int v = sc.nextInt();
		
			vertices[u].forward.add(v);
			vertices[v].backward.add(u);
		}
	}
	
	public void print() {
		for(Vertex v : vertices) {
			System.out.println(v);
		}
	}
	
	public void scc() {
		firstDfsLoop();
		
		secondDfsLoop();
	}
	
	private static int fi;
	private int fiToIndex[];
	private void firstDfsLoop() {
		for(int i=1; i<=vCount; i++) {
			vertices[i].discovered = false;
		}
		
		fi = 0;
		fiToIndex = new int[vCount + 1];
		for(int i=1; i<=vCount; i++) {
			if(!vertices[i].discovered) {
				firstDfs(vertices[i]);
			}
		}
	}
	
	private void firstDfs(Vertex v) {
		v.discovered = true;
		for(int next : v.backward) {
			if(!vertices[next].discovered) {
				firstDfs(vertices[next]);
			}
		}
		
		v.finalIndex = ++fi;
		fiToIndex[fi] = v.id;
	}
	
	private static int c;
	private List<Integer> result = new ArrayList<>();
	private void secondDfsLoop() {
		for(int i=1; i<=vCount; i++) {
			vertices[i].discovered = false;
		}
		
		for(int i=vCount; i>=1; i--) {
			if(!vertices[fiToIndex[i]].discovered) {
				c = 0;
				
				secondDfs(vertices[fiToIndex[i]]);
				
				result.add(c);
			}
		}
	}
	
	private void secondDfs(Vertex v) { 
		v.discovered = true;
		++c;
		for(int next : v.forward) {
			if(!vertices[next].discovered) {
				secondDfs(vertices[next]);
			}
		}
	}
	
	public List<Integer> getResult() {
		Collections.sort(result, Collections.reverseOrder());
		
		return result;
	}
}
