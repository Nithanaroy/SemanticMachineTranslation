package utils;

import java.io.BufferedReader;
import java.io.FileReader;

public class MyFileReader {
	public static String readFile(String filename) throws Exception {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line = br.readLine();
			br.close();
			return line;
		} catch (Exception e) {
			if (Constants.DEBUG) {
				e.printStackTrace();
			}
			throw new Exception(e.getMessage());
		}
	}
}
