package kparser;

import org.python.core.PyException;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

public class PythonRunner {
	public static void main(String[] args) {
		PythonRunner r = new PythonRunner();
		r.runPython("py-stemmer/Sample.py", "greetings");
	}

	/**
	 * Runs the given function in the given filename and returns the string result
	 * 
	 * @param pythonFilePath relative path from home directory of the python module
	 * @param functionName function in that python module to run
	 * @return string result returned by the function. null if an exception
	 */
	public String runPython(String pythonFilePath, String functionName) {
		try {
			PythonInterpreter interpreter = new PythonInterpreter();
			interpreter.execfile(pythonFilePath);
			PyObject buildingClass = interpreter.get(functionName);
			PyObject result = buildingClass.__call__();
			String realResult = (String) result.__tojava__(String.class);
			interpreter.close();
			return realResult;
		} catch (PyException e) {
			e.printStackTrace();
		}
		return null;
	}
}
