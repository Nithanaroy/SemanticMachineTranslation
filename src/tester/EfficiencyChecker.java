package tester;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import main.GermanTranslator;

public class EfficiencyChecker {
	public static boolean DEBUG = true;

	public static void main(String[] args) {
		System.out.println(bagOfWordsComparison("A B C D", "A C E"));
		System.out.println(bagOfWordsComparison("Wiederaufnahme von der Sitzung", "Wiederaufnahme der Sitzungsperiode"));
		System.out.println(bagOfWordsComparison(
				"ich erklären resumed der Sitzung von der europäischen Parlament adjourned auf Freitag 17 Dezember 1999, und ich würde wie einmal wieder zu wünschen Sie ein glücklich neu Jahr in der hoffen dass Sie genossen ein angenehm festlich period.",
				"Ich erkläre die am Freitag, dem 17. Dezember unterbrochene Sitzungsperiode des Europäischen Parlaments für wiederaufgenommen, wünsche Ihnen nochmals alles Gute zum Jahreswechsel und hoffe, daß Sie schöne Ferien hatten."));

		System.out.println();

		System.out.println(oneToOneCompare("A B C D", "A C E"));
		System.out.println(oneToOneCompare("Wiederaufnahme von der Sitzung", "Wiederaufnahme der Sitzungsperiode"));
		System.out.println(oneToOneCompare(
				"ich erklären resumed der Sitzung von der europäischen Parlament adjourned auf Freitag 17 Dezember 1999, und ich würde wie einmal wieder zu wünschen Sie ein glücklich neu Jahr in der hoffen dass Sie genossen ein angenehm festlich period.",
				"Ich erkläre die am Freitag, dem 17. Dezember unterbrochene Sitzungsperiode des Europäischen Parlaments für wiederaufgenommen, wünsche Ihnen nochmals alles Gute zum Jahreswechsel und hoffe, daß Sie schöne Ferien hatten."));

		System.out.println();

		System.out.println(minEditDistance("A B C D", "A C E"));
		System.out.println(minEditDistance("Wiederaufnahme von der Sitzung", "Wiederaufnahme der Sitzungsperiode"));
		System.out.println(minEditDistance(
				"ich erklären resumed der Sitzung von der europäischen Parlament adjourned auf Freitag 17 Dezember 1999, und ich würde wie einmal wieder zu wünschen Sie ein glücklich neu Jahr in der hoffen dass Sie genossen ein angenehm festlich period.",
				"Ich erkläre die am Freitag, dem 17. Dezember unterbrochene Sitzungsperiode des Europäischen Parlaments für wiederaufgenommen, wünsche Ihnen nochmals alles Gute zum Jahreswechsel und hoffe, daß Sie schöne Ferien hatten."));
	}

	/**
	 * Checks the efficiency of the translation by translating each line from English to German and comparing with expected translations
	 * 
	 * @param engFilePath Complete path of the file containing English sentences, one per line
	 * @param germanFilePath Complete path to corresponding German translations, one per line
	 * @param settings 1) maxlines(int): Maximum number of lines to translate and compare (2) stem(bool): decides whether stemming should be done or not while translation
	 * @return
	 * @throws Exception
	 */
	public static HashMap<String, ArrayList<Float>> test(String engFilePath, String germanFilePath, HashMap<Settings, Object> settings)
			throws Exception {

		BufferedReader en = new BufferedReader(new FileReader(engFilePath));
		BufferedReader de = new BufferedReader(new FileReader(germanFilePath));
		GermanTranslator t = GermanTranslator.getInstance();

		settings = getMergedSettings(settings);
		int maxLines = (int) settings.get(Settings.maxLines);
		boolean stem = (boolean) settings.get(Settings.stem);

		HashMap<String, ArrayList<Float>> diffs = new HashMap<>();
		diffs.put("TotalExpectedWords", new ArrayList<Float>(maxLines));
		diffs.put("TotalActualWords", new ArrayList<Float>(maxLines));
		diffs.put("OneToOne", new ArrayList<Float>(maxLines));
		diffs.put("BagCompare", new ArrayList<Float>(maxLines));
		diffs.put("MinEditDistance", new ArrayList<Float>(maxLines));
		diffs.put("BLUE", new ArrayList<Float>(maxLines));

		String eng, gerExpected, gerActual;
		for (int i = 0; i < maxLines; i++) {
			eng = en.readLine();
			gerExpected = de.readLine();

			// gerActual = t.getRawGermanSentence(eng, stem);
			gerActual = t.getGrammaticallyCorrectSentence(eng, settings);

			diffs.get("TotalExpectedWords").add((float) gerExpected.split(" ").length);
			diffs.get("TotalActualWords").add((float) gerActual.split(" ").length);
			diffs.get("OneToOne").add((float) oneToOneCompare(gerActual, gerExpected));
			diffs.get("BagCompare").add((float) bagOfWordsComparison(gerActual, gerExpected));
			diffs.get("MinEditDistance").add((float) minEditDistance(gerActual, gerExpected));
			diffs.get("BLUE").add(unigramBlue(gerActual, gerExpected));

			if (DEBUG) {
				System.out.format("Eng: %s\n\tActualGerman: %s\n\tExpectGerman: %s\n\n", eng, gerActual, gerExpected);
			}
		}
		en.close();
		de.close();
		return diffs;
	}

	/**
	 * Has default settings.
	 * Merges the defaults with provided ones with provided ones being at higher priority
	 * 
	 * @param settings user settings
	 * @return merged settings with default and user given
	 */
	public static HashMap<Settings, Object> getMergedSettings(HashMap<Settings, Object> settings) {
		HashMap<Settings, Object> defaultSettings = new HashMap<>();
		defaultSettings.put(Settings.maxLines, 10);
		defaultSettings.put(Settings.stem, false);
		if (settings != null)
			defaultSettings.putAll(settings);
		return defaultSettings;
	}

	/**
	 * Does a one to one strict comparison between words of both sentences
	 * 
	 * @param s1 Sentence 1
	 * @param s2 Sentence 2
	 * @return Number of mismatched words in s1 and s2
	 */
	private static int oneToOneCompare(String s1, String s2) {
		int cDiffWords = 0; /* Number of words which differ */
		String words1[] = s1.split(" ");
		String words2[] = s2.split(" ");
		int len1 = words1.length;
		int len2 = words2.length;
		int wordDiff[] = new int[Math.max(len1, len2)]; /* Index of words which differ */
		int minLen = Math.min(len1, len2);
		for (int i = 0; i < minLen; i++) {
			if (words1[i].equals(words2[i]))
				wordDiff[i] = 0;
			else {
				wordDiff[i] = 1;
				cDiffWords++;
			}
		}
		return cDiffWords + Math.abs(len2 - len1);
	}

	/**
	 * Finds the best alignment between s1 and s2 and then compares the differences
	 * S1: w1 w2 w3 w4
	 * S2: w2 w4
	 * So the difference is 2
	 * 
	 * @param s1 sentence 1
	 * @param s2 sentence 2
	 * @return
	 */
	public static int minEditDistance(String s1, String s2) {
		int cSpace = 1, cMismatch = 10000;
		String words1[] = s1.split(" ");
		String words2[] = s2.split(" ");
		int len2 = words2.length, len1 = words1.length;
		int a[][] = new int[len1 + 1][len2 + 1];

		// Base case
		for (int i = 0; i <= len2; i++) {
			a[0][i] = cSpace * i;
		}
		for (int i = 0; i <= len1; i++) {
			a[i][0] = cSpace * i;
		}

		for (int i = 1; i <= len1; i++) {
			for (int j = 1; j <= len2; j++) {
				a[i][j] = Math.min(a[i - 1][j - 1] + getCost(words1[i - 1], words2[j - 1], cSpace, cMismatch),
						Math.min(a[i - 1][j] + cSpace, a[i][j - 1] + cSpace));
			}
		}
		return a[len1][len2];
	}

	/**
	 * Back trace after edit distance computation to find the actual alignment of s1 and s2
	 * 
	 * @param s1 Sentence 1
	 * @param s2 Sentence 2
	 */
	public static void getEditDistanceAlignment(String s1, String s2) {
		// TODO
	}

	/**
	 * Compares s1 and s2 considering as a list of words than a sentence. If s1 is a permutation of s2, they are equal
	 * 
	 * @param s1 Sentence 1
	 * @param s2 Sentence 2
	 * @return number of mismatched words in s1 and s2
	 */
	public static int bagOfWordsComparison(String s1, String s2) {
		String words1[] = s1.split(" ");
		String words2[] = s2.split(" ");
		HashMap<String, Integer> wordCount = new HashMap<>();
		for (String w : words1) {
			upsert(wordCount, w.toLowerCase(), 1);
		}
		for (String w : words2) {
			upsert(wordCount, w.toLowerCase(), -1);
		}
		int diff = 0;
		for (int r : wordCount.values()) {
			diff += Math.abs(r);
		}
		return diff;
	}

	/**
	 * Counts the number of words in s1 which were found in s2 of total words in s1
	 * 
	 * @return Percent match considering only unigram BLUE
	 */
	public static float unigramBlue(String s1, String s2) {
		String words1[] = s1.split(" ");
		String words2[] = s2.split(" ");
		HashMap<String, Integer> wordCount = new HashMap<>();
		for (String w : words1) {
			upsert(wordCount, w.toLowerCase(), 1);
		}
		for (String w : words2) {
			upsert(wordCount, w.toLowerCase(), -1);
		}
		int match = 0;
		for (String word : wordCount.keySet()) {
			if (wordCount.get(word) == 0)
				match++;
		}
		return match / (float) words1.length;
	}

	private static void upsert(HashMap<String, Integer> wordCount, String w, int inc) {
		int c = 0;
		if (wordCount.containsKey(w)) {
			c = wordCount.get(w);
			c += inc;
			wordCount.put(w, c);
		} else
			wordCount.put(w, inc);
	}

	private static int getCost(String word1, String word2, int cSpace, int cMistmatch) {
		int cost = 0;
		if (word1.trim().length() == 0 || word2.trim().length() == 0)
			cost = cSpace;
		else if (!word1.equalsIgnoreCase(word2))
			cost = cMistmatch;
		return cost;
	}
}
