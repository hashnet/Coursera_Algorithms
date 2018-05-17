import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Graph {
	private class Vertex {
		private int id;
		private List<Edge> edges = new LinkedList<>();
		
		public Vertex(int id) {
			this.id = id;
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("vertex: [" + id + "]\n");
			for(Edge edge : edges) {
				sb.append("\tedge: ");
				sb.append(edge);
			}
			
			return sb.toString();
		}
	}
	
	private class Edge {
		private Vertex vertexA;
		private Vertex vertexB;
		
		public Edge(Vertex vertexA, Vertex vertexB) {
			this.vertexA = vertexA;
			this.vertexB = vertexB;
		}
		
		@Override
		public String toString() {
			return "[" + vertexA.id + "]-----[" + vertexB.id + "]";
		}
	}
	
	private List<Vertex> vertices = new LinkedList<>();
	private List<Edge> edges = new LinkedList<>();
	private Random rand;
	
	public Graph() {
		vertices = new LinkedList<>();
		edges = new LinkedList<>();
		rand = new Random(System.currentTimeMillis());
	}
	
	public Edge getRandomEdge() {
		if(edges.size() == 0) return null;
		
		int next = rand.nextInt(edges.size());
		return edges.get(next);
	}
	
	public void populateGraph(Scanner sc) {
		int count = sc.nextInt();
		
		Vertex[] tempVertArr = new Vertex[count];
		for(int i=0; i<count; i++) {
			Vertex vertex = new Vertex(i+1);
			tempVertArr[i] = vertex;
			vertices.add(vertex);
		}
		
		sc.nextLine();
		for(int i=0; i<count; i++) {
			String token = sc.nextLine();
			String[] tokens = token.split("\\s+");
			
			Vertex vertexA = tempVertArr[Integer.parseInt(tokens[0]) - 1];
			for(int j=1; j<tokens.length; j++) {
				Vertex vertexB = tempVertArr[Integer.parseInt(tokens[j]) - 1];
			
				boolean found = false;
				for(Edge edge : vertexA.edges) {
					if(edge.vertexA == vertexB || edge.vertexB == vertexB) {
						found = true;
						break;
					}
				}
				
				if(!found) {
					Edge edge = new Edge(vertexA, vertexB);
					edges.add(edge);
					
					vertexA.edges.add(edge);
					vertexB.edges.add(edge);
				}
			}
		}
	}
	
	public void printVertices() {
		System.out.println("Vertices:");
		for(Vertex vertex : vertices) {
			System.out.println(vertex);
		}
	}
	
	public void printEdges() {
		System.out.println("Edges:");
		for(Edge edge : edges) {
			System.out.println(edge);
		}
	}
	
	public int minCut() {
		while(vertices.size() > 2) {
			Edge edgeToDelete = getRandomEdge();
			
			//System.out.println("Diffusing edge: " + edgeToDelete);
			diffuse(edgeToDelete);
		}
		
		return edges.size();
	}
	
	private void diffuse(Edge edge) {
		Vertex vA = edge.vertexA;
		Vertex vB = edge.vertexB;
		
		List<Edge> commonEdges = new ArrayList<>();
		for(Edge edgeOfA : vA.edges) {
			if((edgeOfA.vertexA == vA && edgeOfA.vertexB == vB) || (edgeOfA.vertexA == vB && edgeOfA.vertexB == vA)) {
				commonEdges.add(edgeOfA);
			}
		}
		vA.edges.removeAll(commonEdges);
		vB.edges.removeAll(commonEdges);
		edges.removeAll(commonEdges);
		
		for(int i=0; i<vB.edges.size(); i++) {
			Edge edgeOfB = vB.edges.get(i);
		
			if(edgeOfB.vertexA == vB) {
				edgeOfB.vertexA = vA;
			} else if (edgeOfB.vertexB == vB) {
				edgeOfB.vertexB = vA;
			}
			
			vA.edges.add(edgeOfB);
		}
		
		vertices.remove(vB);
	}
}
