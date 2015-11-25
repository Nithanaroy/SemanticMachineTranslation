package main;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

public class TestRestClient {
	public static void main(String[] args) throws Exception {

		String res = transliterate("Phoenix");
		System.out.println("Done");
	}

	private static String transliterate(String input) throws ClientProtocolException, IOException {
		String s = Request
				.Post("http://webtranslation.paralink.com/target.asp").bodyForm(Form.form().add("source", input).add("dir", "en/de")
						.add("h", "120").add("actions", "translate").add("provider", "google").build())
				.execute().returnContent().asString();

		String startString = "<textarea id=\"text\" name=\"text\" class=\"incell\" onkeydown=\"window.frames['top'].LTR_RTL2();\" dir=\"ltr\" style=\"width: 460px; height: 110px; resize:none;border:none; margin-left: 5px;margin-top:5px;\" cols=\"34\" rows=\"10\" dir=ltr>";
		int start = s.indexOf(startString);
		int end = s.indexOf("</textarea>", start);
		return s.substring(start + startString.length(), end);
	}
}
