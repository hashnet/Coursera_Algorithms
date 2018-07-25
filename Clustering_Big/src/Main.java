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
		private String pattern;
		
		public Node(int id, String pattern) {
			this.id = id;
			this.pattern = pattern;
		}
		
		@Override
		public String toString() {
			return "{"+ String.format("%6d", id) +":" + pattern + "}";
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc = new Scanner(new FileInputStream("files/input.txt"));
		int n = sc.nextInt();
		int bits = sc.nextInt();
		sc.nextLine();

		List<Node> nodes = new ArrayList<>();
		Map<String, List<Node>> map = new HashMap<>();
		for(int i=1; i<=n; i++) {
			String pattern = sc.nextLine();
			pattern = pattern.replaceAll("\\s+", "");
			
			Node node = new Node(i, pattern);
			nodes.add(node);
			
			if(map.containsKey(pattern)) {
				List<Node> newList = map.remove(pattern);
				newList.add(node);
				map.put(pattern, newList);
			} else {
				List<Node> newList = new ArrayList<>();
				newList.add(node);
				map.put(pattern, newList);
			}
		}
		
		UF<Node> uf = new UF<>(nodes);
		
		int maxCluster = doCluster3(nodes, map, uf);
		
		System.out.println(maxCluster);
		sc.close();
	}
	
	private static int doCluster3(List<Node> nodes, Map<String, List<Node>> map, UF<Node> uf) {
		int maxCluster = nodes.size();
		
		//0 dist;
		for(int i=0; i<nodes.size(); i++) {
			Node a = nodes.get(i);
			String pattern = a.pattern;
			
			List<Node> bs = map.get(pattern);
			for(Node b : bs) {
				if(a == b) continue;
				
				if(!uf.connected(a, b)) {
					uf.union(a, b);
					--maxCluster;
				}
			}
		}
		
		//1 dist:
		for(int i=0; i<nodes.size(); i++) {
			Node a = nodes.get(i);
			String pattern = a.pattern;
			
			StringBuilder newPattern = new StringBuilder(pattern);
			for(int pos=0; pos<pattern.length(); pos++) {
				char c = newPattern.charAt(pos);
				if(c == '0') newPattern.setCharAt(pos, '1');
				else newPattern.setCharAt(pos, '0');
					
				if(map.containsKey(newPattern.toString())) {
					List<Node> bs = map.get(newPattern.toString());
					for(Node b : bs) {
						if(a == b) continue;
						
						if(!uf.connected(a, b)) {
							uf.union(a, b);
							--maxCluster;
						}
					}
				}
				newPattern.setCharAt(pos, c);
			}
		}
		
		//2 dist:
		for(int i=0; i<nodes.size(); i++) {
			Node a = nodes.get(i);
			String pattern = a.pattern;
			
			StringBuilder newPattern = new StringBuilder(pattern);
			for(int pos1=0; pos1<pattern.length(); pos1++) {
				char c = newPattern.charAt(pos1);
				if(c == '0') newPattern.setCharAt(pos1, '1');
				else newPattern.setCharAt(pos1, '0');
				
				for(int pos2=pos1+1; pos2<pattern.length(); pos2++) {
					char d = newPattern.charAt(pos2);
					if(d == '0') newPattern.setCharAt(pos2, '1');
					else newPattern.setCharAt(pos2, '0');
						
					if(map.containsKey(newPattern.toString())) {
						List<Node> bs = map.get(newPattern.toString());
						for(Node b : bs) {
							if(a == b) continue;
							
							if(!uf.connected(a, b)) {
								uf.union(a, b);
								--maxCluster;
							}
						}
					}
					newPattern.setCharAt(pos2, d);
				}
				newPattern.setCharAt(pos1, c);
			}
		}
		
		return maxCluster;
	}
}
