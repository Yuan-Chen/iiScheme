package iiScheme;

import java.util.ArrayList;

public class SExp {

	// value of the S-Expression and its getter and setter
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	// branches of the S-Expression and its getter and setter
	private ArrayList<SExp> children;

	public ArrayList<SExp> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<SExp> children) {
		this.children = children;
	}

	// parent of the S-Expression in an ArrayList and its getter and setter
	private SExp parent;

	public SExp getParent() {
		return parent;
	}

	public void setParent(SExp parent) {
		this.parent = parent;
	}

	public SExp(String value, SExp parent) {
		this.value = value;
		this.children = new ArrayList<>();
		this.parent = parent;
	}

	/**
	 * apply method on arguments according to the function table.
	 * 
	 * @param op
	 *            the operator of the method
	 * @param arguments
	 *            arguments in an ArrayList
	 * @param env
	 *            environment in which to find the method
	 * @return result of the calculation
	 */
	public static String compute(String op, ArrayList<String> arguments, Env env) throws Exception {
		for (int i = 0; i < arguments.size(); i++) {
			String str = arguments.get(i);
			if (env.containVariable(str)) {
				arguments.set(i, env.findVariable(str));
			}
		}
		return env.findFunction(op).apply(arguments);
	}

	/**
	 * print the S-Expression structure.
	 * 
	 * @return result as a string
	 */
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("( ");
		for (SExp exp : this.children) {
			if (exp.value.equals("(")) {
				result.append(exp.toString());
			} else {
				result.append(exp.value + " ");
			}
		}
		result.append(") ");
		return result.toString();
	}

	/**
	 * interpret the S-Expression
	 * 
	 * @param exp
	 *            the S-Expression
	 * @param env
	 *            environment in which to interpret the S-Expression
	 * @return the environment after interpretion
	 */
	public static Env Interpret(SExp exp, Env env) throws Exception {
		if (exp.value.equals("(")) {
			for (SExp childExp : exp.children) {
				if (childExp.value.equals("(")) {

					// recursion
					env = Interpret(childExp, env);
				}
			}
			for (SExp childExp : exp.children) {
				env = Interpret(childExp, env);
			}
		}

		// check "define"
		if (exp.value.equals("define") || exp.value.equals("def")) {
			if (exp.parent.children.size() >= 3) {
				env.defineVariable(exp.parent.children.get(1).value, exp.parent.children.get(2).value);
				return env;
			}
			throw new Exception("'Define' fails: not enough arguments.");

			// check if variables can be replaced
		} else if (env.containVariable(exp.value)) {
			exp.value = env.findVariable(exp.value);
			return env;

			// check if indicate a method
		} else if (env.containFunction(exp.value)) {
			String op = exp.value;
			ArrayList<String> arguments = new ArrayList<>();
			for (int i = 1; i < exp.parent.children.size(); i++) {
				arguments.add(exp.parent.children.get(i).value);
			}
			exp.parent.value = compute(op, arguments, env);
			return env;

			// check "if"
		} else if (exp.value.equals("if")) {
			if (exp.parent.children.size() >= 4) {
				if (exp.parent.children.get(1).value.equals("true")) {
					exp.parent.value = exp.parent.children.get(2).value;
				} else {
					exp.parent.value = exp.parent.children.get(3).value;
				}
				return env;
			}
			throw new Exception("'If' fails: not enough arguments.");

			// do nothing
		} else {
			return env;
		}
	}

	/**
	 * check if all children S-Expression are interpretable
	 * 
	 * @return result as a boolean
	 */
	public static boolean isInterpretable(String str) {
		String[] fobbiden = { "(", ")", "[", "]", "{", "}" };
		String[] fobbidenKey = { "def", "define", "if" };
		if (Env.globalEnv.getFunctionList().containsKey(str) || Env.contain(str, fobbiden)
				|| Env.is(str, fobbidenKey)) {
			return true;
		}
		return false;
	}

	/**
	 * if all children S-Expression are not interpretable, result should be a
	 * list of variables
	 * 
	 * @param exp
	 *            the S-Expression
	 * @param env
	 *            environment in which to sub variables
	 * @return the original environment
	 */
	public static Env Evaluate(SExp exp, Env env) throws Exception {
		env = Interpret(exp, env);
		StringBuilder result = new StringBuilder();
		if (exp.value.equals("(")) {
			for (SExp childExp : exp.children) {
				if (!isInterpretable(childExp.value)) {
					result.append(childExp.value + " ");
				} else {
					return env;
				}
			}
			exp.value = result.toString();
		}
		return env;
	}
}
