package main;

import java.util.ArrayList;
import java.util.HashMap;

import tester.EfficiencyChecker;
import tester.Settings;

public class Runner {

	public static void main(String[] args) {

		try {
			 translate();
//			tester();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void tester() {
		// Settings for tester
		HashMap<Settings, Object> mySettings = new HashMap<>();
		mySettings.put(Settings.maxLines, 5);
		mySettings.put(Settings.stem, true);
		mySettings.put(Settings.writeToFileAppendMode, false);
		HashMap<String, ArrayList<Float>> diffLines = null;

		try {
			// diffLines = EfficiencyChecker.test("data/corpus.en", "data/corpus.de", mySettings);
			diffLines = EfficiencyChecker.test("data/test-en-clean", "data/test-de-clean", mySettings);
		} catch (Exception e) {

		}
		System.out.println(diffLines);
		System.out.println("Done");
	}

	private static void translate() throws Exception {
		GermanTranslator t = GermanTranslator.getInstance();

		// TODO: Doesn't work for due to KParser: System.out.println(t.getRawGermanSetence("i ate an apple today"));
		System.out.println("With Stemming");
		System.out.println(t.getRawGermanSentence("John killed his wife who studies at Arizona State University", true));

		// HashMap<Settings, Object> mySettings = new HashMap<>();
		// mySettings.put(Settings.stem, true);
		// mySettings.put(Settings.writeToFileAppendMode, false);
		//
		// t.getGrammaticallyCorrectSentence("John loves Mia ", mySettings);

		// System.out.println("\nWithout Stemming");
		// System.out.println(t.getRawGermanSentence("John loves Mia", false));

		System.out.println("Done");
	}

}
