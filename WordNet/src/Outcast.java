import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
	private final WordNet wordNet;

	// constructor takes a WordNet object
	public Outcast(WordNet wordnet) {
		if (wordnet == null)
			throw new IllegalArgumentException();

		this.wordNet = wordnet;
	}

	// given an array of WordNet nouns, return an outcast
	public String outcast(String[] nouns) {
		if (nouns == null)
			throw new IllegalArgumentException();

		int size = nouns.length;
		if (size == 0)
			return null;

		int[][] dist = new int[size][size];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (i == j)
					dist[i][j] = 0;
				else
					dist[i][j] = -1;
			}
		}

		long max = Long.MIN_VALUE;
		String outcast = null;
		for (int i = 0; i < size; i++) {
			String nounA = nouns[i];
			long sum = 0;
			for (int j = 0; j < size; j++) {
				if (dist[i][j] == -1) {
					int d = wordNet.distance(nounA, nouns[j]);

					dist[i][j] = d;
					dist[j][i] = d;
				}

				sum += dist[i][j];
			}

			if (sum > max) {
				max = sum;
				outcast = nounA;
			}
		}

		return outcast;
	}

	// see test client below
	public static void main(String[] args) {
		WordNet wordnet = new WordNet("files/synsets.txt", "files/hypernyms.txt");
		Outcast outcast = new Outcast(wordnet);

		List<String> outcastFiles = new ArrayList<>();
		outcastFiles.add("files/outcast5.txt");
		outcastFiles.add("files/outcast8.txt");
		outcastFiles.add("files/outcast11.txt");

		for (String outcastFile : outcastFiles) {
			In in = new In(outcastFile);
			String[] nouns = in.readAllStrings();
			StdOut.println(outcastFile + ": " + Arrays.asList(nouns) + "-> " + outcast.outcast(nouns));
		}
	}
}