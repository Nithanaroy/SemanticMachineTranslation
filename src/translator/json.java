package translator;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.json.*;
//import org.json.simple.*;
//import org.json.simple.parser.JSONParser;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.json.simple.parser.*;
//import java.util.*;
//import org.json.simple.parser.*;


class json{
	
	public String subString (org.json.JSONObject json_main, String str) throws JSONException 
	{
		return (json_main.get(str)).toString();
	}
	
	public String subString_2 (JSONObject json_main, String str) throws JSONException 
	{
		return (json_main.get(str)).toString();
	}
 
	public JSONArray subArray(Object obj, String str) throws ParseException
	{
		JSONParser parser = new JSONParser();
		 obj = parser.parse(str);
		return (JSONArray) obj;
	}
	
	public String subStringFromsubArray(JSONArray arr)
	{
		String s=(arr.get(0)).toString();
		return s;
	}
	
	public String posTagConvertor(String str)
	{
		
		if(str.equals(null))
		{
			return "null";
		}
		
		if(str.equals("NNP"))
		{
			str="non";
			return str;
		}
		char c= str.charAt(0);
		try{
			switch(c)
			{
				case 'V' : 
					{
						str="verb";
						break;
					}
				case 'N':
					{
						str="noun";
						break;
					}
				case 'R':
				{
					str="adverb";
					break;
				}
				case 'J':
				{
					str="adjective";
					break;
				}
				case 'D':
				{
					str="determiner";
					break;
				}
				case 'W':
				case 'P':
				{
					str="pronoun";
					break;
				}
				case 'I':
				{	
					str="preposition";
					break;
				}
				case 'C':
				{
					str="conjunction";
					break;
				}
				case 'U':
				{
					str="interjunction";
					break;
				}
				default:
				{
					str="null";
					break;
				}
			
			}
		}
		catch (Exception e)
		{
			System.out.println("pos is null");
			return null;
		}
		
		return str;
	}
	
}
