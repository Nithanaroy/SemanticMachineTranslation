package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class MyFileWriter {
	public static void writeLine(String filename, String value, boolean append) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(filename, append));
			bw.write(value + "\n");
			bw.close();
		} catch (Exception e) {
			System.out.format("Error Writing to file, \"%s\", a value, \"%s\"\n", filename, value);
			e.printStackTrace();
		}
	}
}
