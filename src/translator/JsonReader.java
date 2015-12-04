package translator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import org.json.simple.parser.ParseException;

import utils.Constants;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.*;

public class JsonReader {

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder(); /*
												 * readall builds the contents
												 * of the strings in the buffer
												 */
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream(); /*
													 * takes the input from the url and stores them
													 * in string format
													 */
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd); /*
											 * the string buffer is given as
											 * input to the JSON object
											 */
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}

	}

	public String translate(String input, String pos) throws IOException, JSONException, ParseException {
		String main_result = input;
		String result = "";
		json json_sub = new json();
		String temp_result = "";

		pos = json_sub.posTagConvertor(pos);

		if (Constants.DEBUG) {
			if (pos == null) {
				System.out.println("JSONReader Line 64: " + input + " " + pos);
			}
		}

		try {
			JSONObject json_main = readJsonFromUrl(
					"" + "https://dictionary.yandex.net/api/v1/dicservice.json/lookup?key=dict.1.1.20151025T082334Z.d1c51ebb15e03fc9.b9f5b9c541bd619e3a4fe3ffbb2823fd23fa2c81&lang=en-de&text="
							+ input);

			String s = json_sub.subString(json_main, "def");
			Object obj = null;
			JSONArray arr = json_sub.subArray(obj, s);
			String res_pos = "";

			for (int j = 0; j < arr.size(); j++) {
				org.json.simple.JSONObject sub = (org.json.simple.JSONObject) arr.get(j);

				s = json_sub.subString_2(sub, "tr");
				JSONArray arr1 = json_sub.subArray(obj, s);
				for (int i = 0; i < arr1.size(); i++)

				{
					org.json.simple.JSONObject sub2 = (org.json.simple.JSONObject) arr1.get(i);

					String result_1 = (String) sub2.get("text");

					res_pos = (String) sub2.get("pos");

					if (res_pos.compareTo(pos) == 0)
						return result_1;
					else
						result = result_1;

				}

				/*
				 * if (sub.get("pos") != null) {
				 * res_pos = (String) sub.get("pos");
				 * }
				 * if (sub.get("text") != null) {
				 * result = (String) sub.get("text");
				 * }
				 * if (sub.get("ts") != null) {
				 * temp_result = (String) sub.get("tr");
				 * }
				 */
			}
			if (!result.equals(input) && !(temp_result.equals("")))
				return temp_result;
			else
				return result;
		} /* end of try catch buffer */

		catch (Exception e) {
		}
		return main_result;
	}

	public static void main(String[] args) {
		JsonReader j = new JsonReader();
		try {
			String translated = j.translate("studies", "VBZ");
			System.out.println(translated);
			System.out.println("Done");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
