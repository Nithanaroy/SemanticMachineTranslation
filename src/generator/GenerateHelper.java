package generator;

import utils.PythonRunner;

public class GenerateHelper {

	public static String callAlign(String inputPath, String outputPath) {
		String oneFile = inputPath + "(" + outputPath;
		String filePath = PythonRunner.execute("py-aligner/align.py", "decode", oneFile);
		return filePath;
	}
}
