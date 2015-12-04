package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.python.util.PythonInterpreter;

import generator.GenerateHelper;
import module.graph.helper.JAWSutility;
import module.graph.resources.DependencyParserResource;
import module.graph.resources.NamedEntityTagger;
import tester.EfficiencyChecker;
import tester.Settings;
import translator.JsonReader;
import translator.Transliteration;
import utils.Constants;
import utils.MyFileReader;
import utils.MyFileWriter;
import utils.PythonRunner;

/**
 * Constructs a German [Semantic] Parse tree. Calls KParser to get input sentence's [English] semantic tree.
 * Calls GermanTranslator to translate each word to German keeping in mind the [semantic]
 * context. Returns that Tree
 * 
 * @author nitinpasumarthy
 *
 */
public class GermanTranslator {

	private static GermanTranslator _germanTree = null;
	// private ParserHelper ph = null;
	private DependencyParserResource dr;
	private PythonInterpreter forGermanTenseInterpreter;
	private PythonInterpreter forGermanTenseOfflineInterpreter;
	private PythonInterpreter forAlignmentInterpreter;

	final private String tenseTranslationOfflineFile = "py-stemmer/wordForTense.py";
	final private String tenseTranslationFile = "py-stemmer/perfectGermanWord.py";
	final private String wordAlignerFile = "py-aligner/align.py";

	/**
	 * A SingleTon class
	 */
	private GermanTranslator() {
		/* expensive operations */
		// ph = new ParserHelper();
		dr = new DependencyParserResource();

		forGermanTenseInterpreter = new PythonInterpreter();
		forGermanTenseInterpreter.execfile(tenseTranslationFile);

		forGermanTenseOfflineInterpreter = new PythonInterpreter();
		forGermanTenseOfflineInterpreter.execfile(tenseTranslationOfflineFile);

		forAlignmentInterpreter = new PythonInterpreter();
		forAlignmentInterpreter.execfile(wordAlignerFile);
	}

	@Override
	protected void finalize() throws Throwable {
		// Clean up
		forGermanTenseInterpreter.close();
		forAlignmentInterpreter.close();
	};

	public static GermanTranslator getInstance() {
		if (_germanTree == null) {
			_germanTree = new GermanTranslator();
		}
		return _germanTree;
	}

	/**
	 * Fetches the raw German translation (word to word) for a given sentence considering the semantics
	 * 
	 * @param sentence sentence to translate
	 * @param lemmatize pass true if you want to stem, else false
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 * @throws ParseException
	 */
	public String getRawGermanSentence(String sentence, boolean lemmatize) throws IOException, JSONException, ParseException {
		// Named Entity Recognition
		HashSet<String> namedEntities = getNamedEntities(sentence);

		StringBuilder builder = new StringBuilder();
		HashMap<String, String> posMap = dr.extractDependencies(sentence, false, 0).getPosMap();

		String words[] = sentence.split(" "); // Assumption-1024146
		JAWSutility j = new JAWSutility(); // For lemmatization

		for (int i = 0; i < words.length; i++) {
			String word = words[i];
			String german = "";

			if (namedEntities.contains(word)) {
				german = transliterate(word);
			} else {
				String pos = posMap.get(word + "-" + (i + 1));
				if (lemmatize && pos != null && pos.charAt(0) == 'V') {
					word = j.getBaseForm(word, "v");
				}
				german = getGermanWord(word, pos);
				if (pos != null && pos.charAt(0) == 'V') {
					german = getWordInRightTense(german, pos, true); // UPEN pos also has tense information
				}
			}
			builder.append(german + " ");
		}

		return builder.toString();
	}

	/**
	 * Transliteration of named entities
	 * 
	 * @param word word to transliterate
	 * @return transliterated word in german
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	private String transliterate(String word) throws ClientProtocolException, IOException {
		return Transliteration.transliterate(word);
	}

	/**
	 * Gets the named entities from KParser and split entities when separated by _
	 * 
	 * @param sentence sentence for which named entities are to be found
	 * @return set of named entities
	 */
	private HashSet<String> getNamedEntities(String sentence) {
		NamedEntityTagger.tagNamedEntities(sentence);
		HashMap<String, String> ner = NamedEntityTagger.getStringToNamedEntityMap();
		HashSet<String> words = new HashSet<>();
		for (String key : ner.keySet()) {
			for (String split_key : key.split("_"))
				words.add(split_key);
		}
		return words;
	}

	/**
	 * Calls the German generator which re-arranges the words in the sentence so that it is grammatically correct
	 * 
	 * @param sentence sentence to fix
	 * @param settings A hash of settings like lemmatize
	 * @return Grammatically correct translated sentence in German
	 * @throws Exception
	 */
	public String getGrammaticallyCorrectSentence(String sentence, HashMap<Settings, Object> settings) throws Exception {

		settings = EfficiencyChecker.getMergedSettings(settings);
		boolean lemmatize = (boolean) settings.get(Settings.stem);
		boolean append = (boolean) settings.get(Settings.writeToFileAppendMode);

		String rawGerman = getRawGermanSentence(sentence, lemmatize);
		MyFileWriter.writeLine(Constants.rawSetencesFile, rawGerman, append);
		GenerateHelper.callAlign(forAlignmentInterpreter, Constants.rawSetencesFile, Constants.correctSetencesFile);
		String alignedGerman = MyFileReader.readFile(Constants.correctSetencesFile);

		return alignedGerman;
	}

	/**
	 * Requests for {@literal german} word in a particular tense
	 * 
	 * @param german word for which tense translation has to be done
	 * @param tense tense to which this word has to be transformed
	 * @param useDb if false, makes a REST call, else local saved DB will be used
	 * @return word in requested tense
	 */
	private String getWordInRightTense(String german, String tense, boolean useDB) {
		// return PythonRunner.execute("py-stemmer/perfectGermanWord.py", "perfectWord", german + "####" + tense);
		try {
			if (useDB)
				return PythonRunner.execute(forGermanTenseOfflineInterpreter, "wordInTense", german + "####" + tense);
			return PythonRunner.execute(forGermanTenseInterpreter, "perfectWord", german + "####" + tense);
		} catch (Exception e) {
			if (Constants.ERROR)
				e.printStackTrace();
			if (Constants.DEBUG)
				System.out.format("GermanTranslator Line 181: Could not find %s tense for %s\n", tense, german);
			return german; // return original word on exception
		}
	}

	private <T> ArrayList<T> ArrayToList(T[] words) {
		ArrayList<T> list = new ArrayList<>(words.length);
		for (T word : words) {
			list.add(word);
		}
		return list;
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
