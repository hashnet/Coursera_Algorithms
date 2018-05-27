import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Graph {
	private class Vertex {
		private int id;
		private boolean visited;
		private long minDist;
		
		private List<Edge> edges;
		
		public Vertex(int id) {
			this.id = id;
			this.visited = false;
			this.minDist = Long.MAX_VALUE;
			this.edges = new ArrayList();
		}
		
		@Override
		public String toString() {
			return "vrertex-> id:" + id + ", edges->" + edges;
		}
	}
	
	private class Edge {
		private Vertex next;
		private long length;
		
		public Edge(Vertex next, long length) {
			this.next = next;
			this.length = length;
		}
		
		@Override
		public String toString() {
			return "{id:" + next.id + ", length:" + length + "}";
		}
	}
	private final int max;
	private Vertex[] vertices;
	public Graph(int max) {
		this.max = max;
		
		vertices = new Vertex[max + 1];
		for(int i=1; i<=max; i++)
			vertices[i] = new Vertex(i);
	}
	
	public void populate(Scanner sc) {
		while(sc.hasNextLine() ) {
			String str = sc.nextLine();
			String[] tokens = str.split("\\s+");
			
			int index = Integer.parseInt(tokens[0]);
			Vertex vA = vertices[index];
			
			for(int i=1; i<tokens.length; i++) {
				String[] nl = tokens[i].split(",");
			
				int next = Integer.parseInt(nl[0]);
				if(next <= vA.id) continue;
				Vertex vB = vertices[next]; 
				long length = Long.parseLong(nl[1]);
				
				if(!vA.edges.contains(vB)) 	vA.edges.add(new Edge(vB, length));
				if(!vB.edges.contains(vA))	vB.edges.add(new Edge(vA, length));
			}
		}
	}
	
	private PriorityQueue<Vertex> vHeap = new PriorityQueue<Vertex>(11, new Comparator<Vertex>() {
		@Override
		public int compare(Vertex v1, Vertex v2) {
			if(v1.minDist < v2.minDist) return -1;
			else if(v1.minDist > v2.minDist) return +1;
			else return 0;
		}});
	
	private Vertex source;
	public void initializeDijkstra(int sourceVertexId) {
		Vertex source = vertices[sourceVertexId];
		this.source = source;
		
		source.visited = true;
		source.minDist = 0;
		addEdgeToHeap(source);
	}
	
	private void addEdgeToHeap(Vertex vertex) {
		for(Edge edge : vertex.edges) {
			if(!edge.next.visited) {
				long possibleMinDist = vertex.minDist + edge.length;
				if(vHeap.contains(edge.next)) {
					if(possibleMinDist < edge.next.minDist) {
						vHeap.remove(edge.next);
						edge.next.minDist = possibleMinDist;
						vHeap.add(edge.next);
					}
					
				} else {
					edge.next.minDist = Math.min(vertex.minDist+edge.length, edge.next.minDist);
					vHeap.add(edge.next);
				}
			}
		}
	}
	
	public void dijkstra() {
		while(!vHeap.isEmpty()) {
			Vertex v = vHeap.poll();
			v.visited = true;
			
			addEdgeToHeap(v);
		}
	}
	
	public void printVertex(int vertexId) {
		System.out.println(vertices[vertexId]);
	}
	
	public void printAllVertex() {
		for(int i=1; i<=max; i++)
			printVertex(i);
	}
	
	public long getMinDist(int vertexId) {
		return vertices[vertexId].minDist;
	}
	
	public String getMinDists(int[] vertexIds) {
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<vertexIds.length; i++) {
			if(i>0) sb.append(",");
			
			sb.append(getMinDist(vertexIds[i]));
		}
		
		return sb.toString();
	}
}
