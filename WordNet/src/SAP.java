import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
	private Digraph G;
	
	// constructor takes a digraph (not necessarily a DAG)
	public SAP(Digraph G) {
		this.G = G;
	}

	// length of shortest ancestral path between v and w; -1 if no such path
	public int length(int v, int w) {
		List<Integer> vL = new ArrayList<>();
		vL.add(v);
		
		List<Integer> wL = new ArrayList<>();
		wL.add(w);
		
		return length(vL, wL);
	}

	// a common ancestor of v and w that participates in a shortest ancestral
	// path; -1 if no such path
	public int ancestor(int v, int w) {
		List<Integer> vL = new ArrayList<>();
		vL.add(v);
		
		List<Integer> wL = new ArrayList<>();
		wL.add(w);
		
		return ancestor(vL, wL);
	}

	// length of shortest ancestral path between any vertex in v and any vertex
	// in w; -1 if no such path
	public int length(Iterable<Integer> v, Iterable<Integer> w) {
		return lengthAncestor(v, w).getV();
	}

	// a common ancestor that participates in shortest ancestral path; -1 if no
	// such path
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		return lengthAncestor(v, w).getW();
	}
	
	private Pair<Integer> lengthAncestor(Iterable<Integer> v, Iterable<Integer> w) {
		boolean[] vMarked = new boolean[G.V()];
		int[] vDist = new int[G.V()];
		Queue<Integer> vQ = new ArrayDeque<>();
		
		boolean[] wMarked = new boolean[G.V()];
		int[] wDist = new int[G.V()];
		Queue<Integer> wQ = new ArrayDeque<>();
		
		for(int i : v) {
			vMarked[i] = true;
			vDist[i] = 0;
			vQ.offer(i);
		}
		
		for(int j : w) {
			wMarked[j] = true;
			wDist[j] = 0;
			wQ.offer(j);
		}
		
		boolean isV = true;
		while(!vQ.isEmpty() || !wQ.isEmpty()) {
			if(vQ.isEmpty()) isV = false;
			else if(wQ.isEmpty()) isV = true;
			
			int x = isV ? vQ.poll() : wQ.poll();
			if(vMarked[x] == true && wMarked[x] == true) {
				return new Pair<Integer>(vDist[x] + wDist[x], x);
			}
			
			for(int next : G.adj(x)) {
				if((isV && !vMarked[next]) || (!isV && !wMarked[next]) ) {
					if(isV) {
						vMarked[next] = true;
						vDist[next] = vDist[x] + 1;
						vQ.offer(next);
					} else {
						wMarked[next] = true;
						wDist[next] = wDist[x] + 1;
						wQ.offer(next);
					}
				}
			}
				
			isV = !isV;
		}
		
		return new Pair<Integer>(-1, -1);
	}

	// do unit testing of this class
	public static void main(String[] args) {
		In in = new In("files/digraph1.txt");
		Digraph G = new Digraph(in);
		System.out.println(G);
		
		SAP sap = new SAP(G);
		List<Pair<Integer>> pairs = new ArrayList<>();
		pairs.add(new Pair<>(3, 11));
		pairs.add(new Pair<>(9, 12));
		pairs.add(new Pair<>(7, 2));
		pairs.add(new Pair<>(1, 6));
		pairs.add(new Pair<>(0, 0));
		
		for(Pair<Integer> pair : pairs) {
			int length = sap.length(pair.getV(), pair.getW());
			int ancestor = sap.ancestor(pair.getV(), pair.getW());
			StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
		}
	}
	
	private static class Pair<T> {
		private T v;
		private T w;
		
		public Pair(T v, T w) {
			this.v = v;
			this.w = w;
		}
		
		public T getV() {
			return v;
		}
		
		public T getW() {
			return w;
		}
	}
}