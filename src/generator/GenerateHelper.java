package generator;

import org.python.util.PythonInterpreter;

import utils.PythonRunner;

public class GenerateHelper {

	public static String callAlign(PythonInterpreter p, String inputPath, String outputPath) {
		String oneFile = inputPath + "(" + outputPath;
		String filePath = PythonRunner.execute(p, "align", oneFile);
		return filePath;
	}
}
