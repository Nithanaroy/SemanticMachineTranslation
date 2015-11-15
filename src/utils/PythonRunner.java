package utils;

import org.python.core.PyException;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

public class PythonRunner {
	public static void main(String[] args) {
		// System.out.println(execute("py-stemmer/Sample.py", "greetings2", "Nitin"));
		System.out.println(execute("py-stemmer/Sample.py", "greetings", null));
	}

	/**
	 * Runs the given function in the given filename and returns the string result
	 * 
	 * @param pythonFilePath relative path from home directory of the python module. Eg: py-stemmer/Sample.py
	 * @param functionName function in that python module to run
	 * @return string result returned by the function. null if an exception
	 */
	public static String execute(String pythonFilePath, String functionName, String arg) {
		try {
			PythonInterpreter interpreter = new PythonInterpreter();
			interpreter.execfile(pythonFilePath);
			PyObject buildingClass = interpreter.get(functionName);
			PyObject result;
			if (arg == null)
				result = buildingClass.__call__();
			else
				result = buildingClass.__call__(new PyString(arg));
			String realResult = (String) result.__tojava__(String.class);
			interpreter.close();
			return realResult;
		} catch (PyException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String execute(PythonInterpreter interpreter, String functionName, String arg) {
		try {
			PyObject buildingClass = interpreter.get(functionName);
			PyObject result;
			if (arg == null)
				result = buildingClass.__call__();
			else
				result = buildingClass.__call__(new PyString(arg));
			String realResult = (String) result.__tojava__(String.class);
			return realResult;
		} catch (PyException e) {
			e.printStackTrace();
		}
		return null;
	}
}
