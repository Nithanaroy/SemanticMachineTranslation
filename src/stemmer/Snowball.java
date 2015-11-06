package stemmer;

import java.util.ArrayList;

import kparser.PythonRunner;

public class Snowball extends Stemmer {

	public Snowball(String[] words) {
		super(words);
	}

	@Override
	public ArrayList<String> stem() {
		ArrayList<String> stemmedWords = new ArrayList<>(words.length);
		for (String word : words) {
			stemmedWords.add(PythonRunner.execute(interpreter, "snowball_single", word));
		}
		return stemmedWords;
	}

}
