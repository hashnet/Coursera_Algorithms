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
		In synsetsIn = new In(synsets);
		In hypernymsIn = new In(hypernyms);
		
		maxId = 0;
		populateMap(synsetsIn);
		
		populateDigraph(hypernymsIn);
	}

	private void populateMap(In synsetsIn) {
		nouneMap = new HashMap<>();
		idMap = new HashMap<>();
		
		String line;
		while((line = synsetsIn.readLine()) != null) {
			String[] words = line.split(",");
			
			int id = Integer.parseInt(words[0]);
			if(id > maxId) maxId = id;
			
			idMap.put(id, words[1]);
			String[] nouns = words[1].split("_");
			
			for(String noun : nouns) {
				Set<Integer> ids;
				
				if(nouneMap.containsKey(noun)) {
					ids = nouneMap.get(noun);
				} else {
					ids = new HashSet<>();
				}
				
				ids.add(id);
				nouneMap.put(noun, ids);
			}
		}
	}
	
	private void populateDigraph(In hypernymsIn) {
		G = new  Digraph(maxId + 1);
		
		String line;
		while((line = hypernymsIn.readLine()) != null) {
			String[] words = line.split(",");
			
			int from = Integer.parseInt(words[0]);
			for(int i=1; i<words.length; i++) {
				G.addEdge(from, Integer.parseInt(words[i]));
			}
		}
		
		sap = new SAP(G);
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
		return sap.length(nouneMap.get(nounA), nouneMap.get(nounB));
	}

	// a synset (second field of synsets.txt) that is the common ancestor of
	// nounA and nounB
	// in a shortest ancestral path (defined below)
	public String sap(String nounA, String nounB) {
		int sapId = sap.ancestor(nouneMap.get(nounA), nouneMap.get(nounB));
		if (sapId == -1) return null;
		else return idMap.get(sapId);
	}

	// do unit testing of this class
	public static void main(String[] args) {
		WordNet wordNet = new WordNet("files/synsets15.txt", "files/hypernyms15Tree.txt");
		
		System.out.println(wordNet.nouns());
		
		System.out.println(wordNet.isNoun("a"));
		System.out.println(wordNet.isNoun("z"));
		
		System.out.println(wordNet.distance("a", "o"));
		System.out.println(wordNet.sap("a", "o"));
		
		System.out.println(wordNet.distance("a", "a"));
		System.out.println(wordNet.sap("a", "a"));
	}
}