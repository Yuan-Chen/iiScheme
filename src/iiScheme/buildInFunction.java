package iiScheme;

import java.util.ArrayList;
import java.util.function.Function;

public class buildInFunction {
	
	//primitive methods
	
	public static Function<ArrayList<String>, String> add = (arguments) -> {
		long result = 0;
		for (String str : arguments) {
			result = result + Long.parseLong(str);
		}
		return Long.valueOf(result).toString();
	};

	public static Function<ArrayList<String>, String> minus = (arguments) -> {
		long result = Long.parseLong(arguments.get(0));
		arguments.remove(0);
		for (String str : arguments) {
			result = result - Long.parseLong(str);
		}
		return Long.valueOf(result).toString();
	};

	public static Function<ArrayList<String>, String> multiply = (arguments) -> {
		long result = 1;
		for (String str : arguments) {
			result = result * Long.parseLong(str);
		}
		return Long.valueOf(result).toString();
	};

	public static Function<ArrayList<String>, String> devide = (arguments) -> {
		long result = Long.parseLong(arguments.get(0));
		arguments.remove(0);
		for (String str : arguments) {
			result = result / Long.parseLong(str);
		}
		return Long.valueOf(result).toString();
	};

	public static Function<ArrayList<String>, String> mod = (arguments) -> {
		long result = Long.parseLong(arguments.get(0));
		arguments.remove(0);
		for (String str : arguments) {
			result = result % Long.parseLong(str);
		}
		return Long.valueOf(result).toString();
	};

	public static Function<ArrayList<String>, String> not = (arguments) -> {
		return Boolean.valueOf(!Boolean.parseBoolean(arguments.get(0))).toString();
	};

	public static Function<ArrayList<String>, String> and = (arguments) -> {
		for (String str : arguments) {
			if (Boolean.parseBoolean(str) == false) {
				return "false";
			}
		}
		return "true";
	};

	public static Function<ArrayList<String>, String> or = (arguments) -> {
		for (String str : arguments) {
			if (Boolean.parseBoolean(str) == true) {
				return "true";
			}
		}
		return "false";
	};

	public static Function<ArrayList<String>, String> equals = (arguments) -> {
		if (arguments.size() <= 1) {
			return "false";
		}
		for (int i = 0; i < arguments.size() - 1; i++) {
			if (arguments.get(i).equals(arguments.get(i + 1)) == false) {
				return "false";
			}
		}
		return "true";
	};

	public static Function<ArrayList<String>, String> smaller = (arguments) -> {
		if (arguments.size() <= 1) {
			return "false";
		}
		for (int i = 0; i < arguments.size() - 1; i++) {
			if (arguments.get(i).compareTo(arguments.get(i + 1)) >= 0) {
				return "false";
			}
		}
		return "true";
	};

	public static Function<ArrayList<String>, String> greater = (arguments) -> {
		if (arguments.size() <= 1) {
			return "false";
		}
		for (int i = 0; i < arguments.size() - 1; i++) {
			if (arguments.get(i).compareTo(arguments.get(i + 1)) <= 0) {
				return "false";
			}
		}
		return "true";
	};

	public static Function<ArrayList<String>, String> smallerOrEquals = (arguments) -> {
		if (arguments.size() <= 1) {
			return "false";
		}
		for (int i = 0; i < arguments.size() - 1; i++) {
			if (arguments.get(i).compareTo(arguments.get(i + 1)) > 0) {
				return "false";
			}
		}
		return "true";
	};

	public static Function<ArrayList<String>, String> greaterOrEquals = (arguments) -> {
		if (arguments.size() <= 1) {
			return "false";
		}
		for (int i = 0; i < arguments.size() - 1; i++) {
			if (arguments.get(i).compareTo(arguments.get(i + 1)) < 0) {
				return "false";
			}
		}
		return "true";
	};
}
