package stemmer;

import java.util.ArrayList;

import org.python.util.PythonInterpreter;

public abstract class Stemmer {
	protected String[] words;
	final protected String PythonStemmerFilePath = "py-stemmer/stemmers.py";
	protected PythonInterpreter interpreter;

	public Stemmer(String[] words) {
		this.words = words;
		
		interpreter = new PythonInterpreter();
		interpreter.execfile(PythonStemmerFilePath);
	}

	public abstract ArrayList<String> stem();
}
