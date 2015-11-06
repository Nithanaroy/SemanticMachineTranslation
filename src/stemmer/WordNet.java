package stemmer;

import java.util.ArrayList;

import kparser.PythonRunner;

public class WordNet extends Stemmer {

	public WordNet(String[] words) {
		super(words);
	}

	@Override
	public ArrayList<String> stem() {
		ArrayList<String> stemmedWords = new ArrayList<>(words.length);
		for (String word : words) {
			stemmedWords.add(PythonRunner.execute(interpreter, "wordnet_single", word));
		}
		return stemmedWords;
	}

}
