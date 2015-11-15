package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.simple.parser.ParseException;

import tester.EfficiencyChecker;
import tester.Settings;

public class Runner {

	public static void main(String[] args) throws IOException, JSONException, ParseException {

		// Settings for tester
		HashMap<Settings, Object> mySettings = new HashMap<>();
		mySettings.put(Settings.maxLines, 2);
		mySettings.put(Settings.stem, true);

		HashMap<String, ArrayList<Integer>> diffLines = EfficiencyChecker.test(
				"/Volumes/350GB/Projects/NLP/SemanticMachineTranslation/data/europarl-v7.de-en.en",
				"/Volumes/350GB/Projects/NLP/SemanticMachineTranslation/data/europarl-v7.de-en.de", mySettings);
		System.out.println(diffLines);
		System.out.println("Done");
	}

	private static void translate() throws IOException, JSONException, ParseException {
		GermanTranslator t = GermanTranslator.getInstance();

		// TODO: Doesn't work for due to KParser: System.out.println(t.getRawGermanSetence("i ate an apple today"));
		System.out.println("With Stemming");
		System.out.println(t.getRawGermanSentence("John loves Mia", true));

		System.out.println("\nWithout Stemming");
		System.out.println(t.getRawGermanSentence("John loves Mia", false));
	}

}
