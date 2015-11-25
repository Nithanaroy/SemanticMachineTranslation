package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.simple.parser.ParseException;

import tester.EfficiencyChecker;
import tester.Settings;

public class Runner {

	public static void main(String[] args) {

		try {
			translate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void tester() throws IOException, JSONException, ParseException {
		// Settings for tester
		HashMap<Settings, Object> mySettings = new HashMap<>();
		mySettings.put(Settings.maxLines, 29);
		mySettings.put(Settings.stem, true);

		HashMap<String, ArrayList<Integer>> diffLines = EfficiencyChecker.test(
				"/home/alekhya/en",
				"/home/alekhya/dee", mySettings);
		System.out.println(diffLines);
		System.out.println("Done");
	}

	private static void translate() throws Exception {
		GermanTranslator t = GermanTranslator.getInstance();

		// TODO: Doesn't work for due to KParser: System.out.println(t.getRawGermanSetence("i ate an apple today"));
<<<<<<< HEAD
		//System.out.println("With Stemming");
		//System.out.println(t.getRawGermanSentence("John loves Mia", true));

		System.out.println("\nWithout Stemming");
		System.out.println(t.getRawGermanSentence("john studies at asu", false));
=======
		System.out.println("With Stemming");
		System.out.println(t.getRawGermanSentence("John killed his wife who studies as Arizona State University", true));

		// HashMap<Settings, Object> mySettings = new HashMap<>();
		// mySettings.put(Settings.stem, true);
		// mySettings.put(Settings.writeToFileAppendMode, false);
		//
		// t.getGrammaticallyCorrectSentence("John loves Mia ", mySettings);

		// System.out.println("\nWithout Stemming");
		// System.out.println(t.getRawGermanSentence("John loves Mia", false));

		System.out.println("Done");
>>>>>>> 6528d8b2abcf44c0b243826a22cb23f627e4e392
	}

}
