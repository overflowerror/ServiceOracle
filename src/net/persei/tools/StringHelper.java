package net.persei.tools;

public class StringHelper {
	public static String repeat(int length, String string) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < length; i++) {
			builder.append(string);
		}
		return builder.toString();
	}
}
