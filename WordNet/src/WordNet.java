import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

public class WordNet {
	private Map<String, Set<Integer>> nouneMap;
	private Map<Integer, String> idMap;
	private Digraph G;
	private SAP sap;
	private int maxId;

	// constructor takes the name of the two input files
	public WordNet(String synsets, String hypernyms) {
		if (synsets == null || hypernyms == null)
			throw new IllegalArgumentException();

		In synsetsIn = new In(synsets);
		In hypernymsIn = new In(hypernyms);

		maxId = 0;
		populateMap(synsetsIn);

		populateDigraph(hypernymsIn);

		Cycle cycle = new Cycle(G);
		if (!cycle.isProperRooted())
			throw new IllegalArgumentException("The Digraph is not properly rooted.");
		if (cycle.hasCycle())
			throw new IllegalArgumentException("There is cycle in the Digraph");
	}

	private void populateMap(In synsetsIn) {
		nouneMap = new HashMap<>();
		idMap = new HashMap<>();

		while (!synsetsIn.isEmpty()) {
			String line = synsetsIn.readLine();
			String[] words = line.split(",");

			int id = Integer.parseInt(words[0]);
			if (id > maxId)
				maxId = id;

			idMap.put(id, words[1]);
			String[] nouns = words[1].split(" ");

			for (String noun : nouns) {
				Set<Integer> ids = nouneMap.get(noun);
				if (ids == null)
					ids = new HashSet<>();

				ids.add(id);
				nouneMap.put(noun, ids);
			}
		}
	}

	private void populateDigraph(In hypernymsIn) {
		G = new Digraph(maxId + 1);

		while (!hypernymsIn.isEmpty()) {
			String line = hypernymsIn.readLine();
			String[] words = line.split(",");

			int from = Integer.parseInt(words[0]);
			for (int i = 1; i < words.length; i++) {
				G.addEdge(from, Integer.parseInt(words[i]));
			}
		}

		sap = new SAP(G);
	}

	private class Cycle {
		private final Digraph G;
		private boolean[] marked;
		private boolean[] onStack;

		public Cycle(Digraph G) {
			this.G = G;
			marked = new boolean[G.V()];
			onStack = new boolean[G.V()];
		}

		public boolean hasCycle() {
			for (int v = 0; v < G.V(); v++) {
				if (!marked[v]) {
					if (dfsHasCycle(v))
						return true;
				}
			}

			return false;
		}

		private boolean dfsHasCycle(int v) {
			marked[v] = true;
			onStack[v] = true;

			for (int next : G.adj(v)) {
				if (!marked[next]) {
					if (dfsHasCycle(next))
						return true;
				} else if (onStack[next])
					return true;
			}

			onStack[v] = false;
			return false;
		}

		public boolean isProperRooted() {
			int rootCount = 0;

			for (int v = 0; v < G.V(); v++) {
				if (G.indegree(v) > 0 && G.outdegree(v) == 0)
					++rootCount;
			}

			return rootCount == 1 ? true : false;
		}
	}

	// returns all WordNet nouns
	public Iterable<String> nouns() {
		return nouneMap.keySet();
	}

	// is the word a WordNet noun?
	public boolean isNoun(String word) {
		return nouneMap.containsKey(word);
	}

	// distance between nounA and nounB (defined below)
	public int distance(String nounA, String nounB) {
		if (!isNoun(nounA) || !isNoun(nounB))
			throw new IllegalArgumentException();

		return sap.length(nouneMap.get(nounA), nouneMap.get(nounB));
	}

	// a synset (second field of synsets.txt) that is the common ancestor of
	// nounA and nounB
	// in a shortest ancestral path (defined below)
	public String sap(String nounA, String nounB) {
		if (!isNoun(nounA) || !isNoun(nounB))
			throw new IllegalArgumentException();

		int sapId = sap.ancestor(nouneMap.get(nounA), nouneMap.get(nounB));
		if (sapId == -1)
			return null;
		else
			return idMap.get(sapId);
	}

	// do unit testing of this class
	public static void main(String[] args) {
		WordNet wordNet = new WordNet("files/synsets15.txt", "files/hypernyms15Tree.txt");
		// WordNet wordNet = new WordNet("files/synsets.txt",
		// "files/hypernyms.txt");
		// WordNet wordNet = new WordNet("files/synsets3.txt",
		// "files/hypernyms3InvalidCycle.txt");
		// WordNet wordNet = new WordNet("files/synsets3.txt",
		// "files/hypernyms3InvalidTwoRoots.txt");

		System.out.println(wordNet.nouns());

		System.out.println(wordNet.isNoun("a"));
		System.out.println(wordNet.isNoun("z"));

		System.out.println(wordNet.distance("a", "o"));
		System.out.println(wordNet.sap("a", "o"));

		System.out.println(wordNet.distance("a", "a"));
		System.out.println(wordNet.sap("a", "a"));
	}
}