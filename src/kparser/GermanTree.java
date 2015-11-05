package kparser;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONException;
import org.json.simple.parser.ParseException;

import module.graph.ParserHelper;
import module.graph.resources.DependencyParserResource;
import module.graph.resources.InputDependencies;

/**
 * Constructs a German [Semantic] Parse tree. Calls KParser to get input sentence's [English] semantic tree. Calls GermanTranslator to translate each word to German keeping in mind the [semantic]
 * context. Returns that Tree
 * 
 * @author nitinpasumarthy
 *
 */
public class GermanTree {

	private static GermanTree _germanTree = null;
	private ParserHelper ph = null;
	DependencyParserResource dr;

	/**
	 * A SingleTon class
	 */
	private GermanTree() {
		/* expensive operations */
		// ph = new ParserHelper();
		dr = new DependencyParserResource();
	}

	public static GermanTree getInstance() {
		if (_germanTree == null) {
			_germanTree = new GermanTree();
		}
		return _germanTree;
	}

	public String getRawGermanSetence(String sentence) throws IOException, JSONException, ParseException {
		StringBuilder builder = new StringBuilder();
		InputDependencies iDeps = dr.extractDependencies(sentence, false, 0);
		HashMap<String, String> posMap = iDeps.getPosMap();

		String words[] = sentence.split(" "); // Assumption-1024146

		for (int i = 0; i < words.length; i++) {
			String word = words[i];
			String pos = posMap.get(word + "-" + (i + 1));
			String german = getGermanWord(word, pos);
			builder.append(german + " ");
		}

		return builder.toString();
	}

	/**
	 * Calls the German translator API which uses WordNET and GermanNET to find the right German word for the given English word and its POS
	 * 
	 * @param word English word to translate
	 * @param pos Parts of speech of the word
	 * @return German Word
	 * @throws ParseException
	 * @throws JSONException
	 * @throws IOException
	 */
	private String getGermanWord(String word, String pos) throws IOException, JSONException, ParseException {
		JsonReader read = new JsonReader();
		return read.translate(word, pos);
	}

	/**
	 * Finds the parts of speech (POS) for a given word using the KParser Tree
	 * Note: No longer used as another class of KParser gives POS in a more consumable way
	 * 
	 * @param tree Kparser tree (JSON format)
	 * @param word word to find the POS for
	 * @param word_index zero based index of the word in the sentence
	 * @return a POS tag from Penn tree bank (this is used default by KParser)
	 */
	private String findPOSFromTree(String tree, String word, int word_index) {
		// TODO: Can use a regex to find the word in tree. That is more elegant
		String pos = "NN"; // TODO: Just to be safe in any exception. Will be taken care if JSON is parsed
		try {
			String pos_start_key = "\"pos\":\"", pos_end_key = "\",";
			int word_in_tree = tree.toLowerCase().indexOf(word.toLowerCase() + "-" + (word_index + 1));
			int pos_index_in_tree = tree.indexOf(pos_start_key, word_in_tree); // "pos":"
			int end_of_line_index_in_tree = tree.indexOf(pos_end_key, pos_index_in_tree); // "},
			pos = tree.substring(pos_index_in_tree + pos_start_key.length(), end_of_line_index_in_tree);
		} catch (Exception e) {
			// TODO: Log the exception
		}
		return pos;
	}
}
