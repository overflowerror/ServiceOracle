package net.persei.tools;

import java.util.Arrays;

public class ArrayHelper {
	public static <E> E[] fillArray(E[] array, E value) {
		Arrays.fill(array, value);
		return array;
	}
}
