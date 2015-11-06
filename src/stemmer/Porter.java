package stemmer;

import java.util.ArrayList;

import kparser.PythonRunner;

public class Porter extends Stemmer {

	public Porter(String[] words) {
		super(words);
	}

	@Override
	public ArrayList<String> stem() {
		ArrayList<String> stemmedWords = new ArrayList<>(words.length);
		for (String word : words) {
			stemmedWords.add(PythonRunner.execute(interpreter, "porter_single", word));
		}
		return stemmedWords;
	}

}
