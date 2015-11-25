package stemmer;

import java.util.ArrayList;

import utils.PythonRunner;

public class Lancaster extends Stemmer {

	public Lancaster(String[] words) {
		super(words);
	}

	@Override
	public ArrayList<String> stem() {
		ArrayList<String> stemmedWords = new ArrayList<>(words.length);
		for (String word : words) {
			stemmedWords.add(PythonRunner.execute(interpreter, "lancaster_single", word));
		}
		return stemmedWords;
	}

}
