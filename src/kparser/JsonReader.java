package kparser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import java.net.URL;
import java.nio.charset.Charset;

import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.*;

public class JsonReader {

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();   /* readall builds the contents of the strings in the buffer */
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static JSONObject readJsonFromUrl(String url) throws IOException,
			JSONException {
		InputStream is = new URL(url).openStream();    /* takes the input from the url and stores them in string format */
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is,
					Charset.forName("UTF-8")));
			String jsonText = readAll(rd);              /* the string buffer is given as input to the JSON object */
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}

	}

	public String translate(String input, String pos) throws IOException, JSONException,
			ParseException {
		String result = input;
		try {
			JSONObject json = readJsonFromUrl("https://dictionary.yandex.net/api/v1/dicservice.json/lookup?key=dict.1.1.20151025T082334Z.d1c51ebb15e03fc9.b9f5b9c541bd619e3a4fe3ffbb2823fd23fa2c81&lang=en-de&text="
					+ input);
			String s = (json.get("def")).toString(); /* extracts the json object with respect to "def" variable */

			JSONParser parser = new JSONParser();
			Object obj = parser.parse(s);          /* parsers the array in JsonArray format */
			JSONArray array = (JSONArray) obj;

			s = (array.get(0)).toString();        /* gets the string format of the array */
			json = new JSONObject(s);             /* returns the string as JSON object */

			s = (json.get("tr")).toString();      /* gets the json array refer to "tr" variable */
			obj = parser.parse(s);
			array = (JSONArray) obj;             /* parses the array to get the JSON object */

			s = (array.get(0)).toString();

			json = new JSONObject(s);
			result = (json.get("text")).toString();  /* gets the "text content of the translated word */
			// System.out.println(json.get("text"));

			/*
			 * try{
			 * 
			 * System.out.println(json.get("syn")); } catch (JSONException e) {
			 * 
			 * }
			 */
			
			  try{
			  
			 String german_pos=(json.getString("pos")).toString();  /* getting the POS tag o fthe transalated word */
			 	if(german_pos==pos)
			 		return result;
			  } 
			  
			  catch (JSONException e) {
			  
			  }
			 
		} catch (Exception e) {
			// TODO Log such words for later phase
		}

		return result;
	}

}