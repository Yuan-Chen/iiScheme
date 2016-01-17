package iiScheme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

public class Env {

	// hashmap to store method reference
	private HashMap<String, Function<ArrayList<String>, String>> functionList;

	public HashMap<String, Function<ArrayList<String>, String>> getFunctionList() {
		return functionList;
	}

	// hashmap to store variable reference
	private HashMap<String, String> variableList;

	public HashMap<String, String> getVariableList() {
		return variableList;
	}

	// parent environment
	private Env parent;

	// constructor
	public Env(Env parent) {
		this.functionList = new HashMap<String, Function<ArrayList<String>, String>>();
		this.variableList = new HashMap<String, String>();
		this.parent = parent;
	}

	// default environment
	public static Env globalEnv = getGlobalEnv();

	public static Env getGlobalEnv() {
		Env global = new Env(null);
		global.functionList.put("+", buildInFunction.add);
		global.functionList.put("-", buildInFunction.minus);
		global.functionList.put("*", buildInFunction.multiply);
		global.functionList.put("/", buildInFunction.devide);
		global.functionList.put("%", buildInFunction.mod);
		global.functionList.put("not", buildInFunction.not);
		global.functionList.put("and", buildInFunction.and);
		global.functionList.put("=", buildInFunction.equals);
		global.functionList.put("<", buildInFunction.smaller);
		global.functionList.put(">", buildInFunction.greater);
		global.functionList.put(">=", buildInFunction.smallerOrEquals);
		global.functionList.put("<=", buildInFunction.greaterOrEquals);
		return global;
	}

	/**
	 * check if the variable is in the table
	 * 
	 * @param name
	 *            the key
	 * @return true if the variable is in the table
	 */
	public boolean containVariable(String str) {
		Env current = this;
		while (current != null) {
			if (current.variableList.containsKey(str)) {
				return true;
			}
			current = current.parent;
		}
		return false;
	}

	/**
	 * find string in variable table
	 * 
	 * @param name
	 *            the key
	 * @return the corresponding value
	 */
	public String findVariable(String name) throws Exception {
		Env current = this;
		while (current != null) {
			if (current.variableList.containsKey(name)) {
				return current.variableList.get(name);
			}
			current = current.parent;
		}
		throw new Exception(name + " is not defined as a variable.");
	}

	/**
	 * define a key in the variable table
	 * 
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 * @return true if the method is in the table
	 */
	public Env defineVariable(String key, String value) throws Exception {
		if (canDefine(key)) {
			if (this.variableList.containsKey(key)) {
				this.variableList.remove(key);
			}
			this.variableList.put(key, value);
			System.out.println("        >>" + key + " is defined to be " + value);
			return this;
		}
		throw new Exception(key + " can not be defined as a variable");
	}

	/**
	 * check if the method is in the table
	 * 
	 * @param name
	 *            the key
	 * @return true if the method is in the table
	 */
	public boolean containFunction(String name) throws Exception {
		Env current = this;
		while (current != null) {
			if (current.functionList.containsKey(name)) {
				return true;
			}
			current = current.parent;
		}
		return false;
	}

	/**
	 * find method in function table
	 * 
	 * @param name
	 *            the key
	 * @return the corresponding value
	 */
	public Function<ArrayList<String>, String> findFunction(String name) throws Exception {
		Env current = this;
		while (current != null) {
			if (current.functionList.containsKey(name)) {
				return current.functionList.get(name);
			}
			current = current.parent;
		}
		throw new Exception(name + " is not defined as a function.");
	}

	/**
	 * check if a string contains char of all string in a string array
	 * 
	 * @param a
	 *            target string
	 * @param b
	 *            target string array
	 * @return true if any part of string a is equal to any element in string
	 *         array
	 */
	public static boolean contain(String a, String[] b) {
		for (String str : b) {
			if (str.length() <= a.length()) {
				int length = str.length();
				int def = a.length() - str.length();
				for (int i = 0; i <= def; i++) {
					String temp = a.substring(i, i + length);
					if (temp.equals(str)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * check if a string is equal to any element in a string array
	 * 
	 * @param a
	 *            target string
	 * @param b
	 *            target string array
	 * @return true if string a is equal to any element in string array
	 */
	public static boolean is(String a, String[] b) {
		for (String str : b) {
			if (str.equals(a)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * check if a string can be defined
	 * 
	 * @param str
	 *            target string
	 * @return true if the string can be defined
	 */
	public static boolean canDefine(String str) {
		String[] fobbiden = { "(", ")", "[", "]", "{", "}" };
		String[] fobbidenKey = { "true", "false", "def", "define", "if" };
		if (globalEnv.functionList.containsKey(str) || contain(str, fobbiden) || is(str, fobbidenKey)) {
			return false;
		}
		return true;
	}

}
