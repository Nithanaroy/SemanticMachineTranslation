package kparser;

import java.io.IOException;

import org.json.JSONException;
import org.json.simple.parser.ParseException;

public class Runner {

	public static void main(String[] args) throws IOException, JSONException, ParseException {
		GermanTree t = GermanTree.getInstance();

		//TODO: Doesn't work for due to KParser: System.out.println(t.getRawGermanSetence("i ate an apple today"));
		System.out.println(t.getRawGermanSetence("i ate an apple today"));
		System.out.println("Done");
	}

}
