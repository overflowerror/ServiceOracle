package net.persei.tools;

import java.math.BigInteger;

public class Base62 {
	
	private static final BigInteger SIXTY_TWO = BigInteger.valueOf(62);
	
	private static char toChar(byte value) {
		if (value < 0)
			throw new IllegalArgumentException("< 0");
		if (value < 10)
			return (char) ('0' + value);
		if (value < 36)
			return (char) ('A' + value - 10);
		if (value < 62)
			return (char) ('a' + value - 36);
		throw new IllegalArgumentException("> 61");			
	}
	
	private static byte toByte(char value) {
		if (value >= '0' && value <= '9')
			return (byte) (value - '0');
		if (value >= 'A' && value <= 'Z')
			return (byte) (value - 'A' + 10);
		if (value >= 'a' && value <= 'z')
			return (byte) (value - 'a' + 36);
		throw new IllegalArgumentException("not a valid base62 char");		
	}
	
	public static byte[] decode(String key) {
		BigInteger value = BigInteger.ZERO;
		
		for (int i = 0; i < key.length(); i++) {
			value = value.multiply(SIXTY_TWO);
			value = value.add(BigInteger.valueOf(toByte(key.charAt(i))));
		}
		
		return value.toByteArray();
	}
	
	public static String encode(byte key[]) {
		BigInteger i = new BigInteger(key);
		
		StringBuilder builder = new StringBuilder();
		
		while (i.compareTo(BigInteger.ZERO) > 0) {
			builder.append(toChar(i.mod(SIXTY_TWO).byteValue()));
			i = i.divide(SIXTY_TWO);
		}
		
		return builder.reverse().toString();
	}
}
