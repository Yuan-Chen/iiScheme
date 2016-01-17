/**
 * @author Yuan Chen
 * 
 * @version 0.1
 */

package iiScheme;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class iiScheme {

	/**
	 * Tokenize the string.
	 * 
	 * @ @param
	 *       str the string to be tokenized
	 * @return tokens in an array of string
	 */
	private static String[] Tokenize(String str) {
		StringTokenizer tokenizer = new StringTokenizer(str.replace("(", " ( ").replace(")", " ) "), " ");
		ArrayList<String> result = new ArrayList<>();
		while (tokenizer.hasMoreTokens()) {
			result.add(tokenizer.nextToken());
		}
		return result.toArray(new String[result.size()]);
	}

	/**
	 * Parse the String into S-Expression.
	 * 
	 * @param code
	 *            the string to be parsed
	 * @return a reference to the root of S-Expression
	 */
	private static SExp Parse(String code) {
		SExp root = new SExp("", null);
		SExp current = root;
		for (String token : Tokenize(code)) {
			if (token.equals("(")) {
				SExp newNode = new SExp("(", current);
				current.getChildren().add(newNode);
				current = newNode;
			} else if (token.equals(")")) {
				current = current.getParent();
			} else {
				current.getChildren().add(new SExp(token, current));
			}
		}
		return root.getChildren().get(0);
	}

	public static void main(String... args) {
		try {
			Env env = Env.globalEnv;
			Scanner input = new Scanner(System.in);

			// REPL
			while (true) {
				System.out.print("iiScheme>>");
				String code = input.nextLine();
				if (code.equals("exit") || code.equals("quit")) {
					break;
				}
				SExp result = Parse(code);
				env = SExp.Evaluate(result, env);

				// check if no answer is generated
				if (!result.getValue().equals("(")) {
					System.out.println("        >>" + result.getValue());
				}
			}
			input.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
