import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
	private final Digraph G;

	// constructor takes a digraph (not necessarily a DAG)
	public SAP(Digraph G) {
		if (G == null)
			throw new IllegalArgumentException();

		this.G = new Digraph(G);
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
		if (v == null || w == null)
			throw new IllegalArgumentException();

		return lengthAncestor(v, w).getV();
	}

	// a common ancestor that participates in shortest ancestral path; -1 if no
	// such path
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		if (v == null || w == null)
			throw new IllegalArgumentException();

		return lengthAncestor(v, w).getW();
	}

	private Pair<Integer> lengthAncestor(Iterable<Integer> v, Iterable<Integer> w) {
		int maxVertex = G.V();

		for (Integer i : v) {
			if (i == null || i < 0 || i >= maxVertex)
				throw new IllegalArgumentException();
		}
		for (Integer j : w) {
			if (j == null || j < 0 || j >= maxVertex)
				throw new IllegalArgumentException();
		}

		boolean[] vMarked = new boolean[G.V()];
		int[] vDist = new int[G.V()];
		Queue<Integer> vQ = new ArrayDeque<>();
		for (int i : v) {
			vDist[i] = 0;
			vMarked[i] = true;
			vQ.add(i);
		}
		while (!vQ.isEmpty()) {
			int curr = vQ.poll();
			for (int next : G.adj(curr)) {
				if (!vMarked[next]) {
					vDist[next] = vDist[curr] + 1;
					vMarked[next] = true;
					vQ.add(next);
				}
			}
		}

		int minDist = Integer.MAX_VALUE;
		int minAnc = -1;
		boolean[] wMarked = new boolean[G.V()];
		int[] wDist = new int[G.V()];
		Queue<Integer> wQ = new ArrayDeque<>();
		for (int j : w) {
			wDist[j] = 0;
			if (vMarked[j]) {
				int dist = vDist[j] + wDist[j];
				if (dist < minDist) {
					minDist = dist;
					minAnc = j;
				}
			}
			wMarked[j] = true;
			wQ.add(j);
		}
		while (!wQ.isEmpty()) {
			int curr = wQ.poll();
			for (int next : G.adj(curr)) {
				if (!wMarked[next]) {
					wDist[next] = wDist[curr] + 1;
					if (vMarked[next]) {
						int dist = vDist[next] + wDist[next];
						if (dist < minDist) {
							minDist = dist;
							minAnc = next;
						}
					}
					wMarked[next] = true;
					wQ.add(next);
				}
			}
		}

		if (minDist == Integer.MAX_VALUE)
			minDist = -1;

		return new Pair<Integer>(minDist, minAnc);
	}

	// do unit testing of this class
	public static void main(String[] args) {
		In in = new In("files/digraph5.txt");
		Digraph G = new Digraph(in);
		System.out.println(G);

		SAP sap = new SAP(G);
		List<Pair<Integer>> pairs = new ArrayList<>();
		pairs.add(new Pair<>(17, 21));

		for (Pair<Integer> pair : pairs) {
			int length = sap.length(pair.getV(), pair.getW());
			int ancestor = sap.ancestor(pair.getV(), pair.getW());
			StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
		}
	}

	private static class Pair<T> {
		private final T v;
		private final T w;

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