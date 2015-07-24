package com.CentrumGuy.CodWarfare.Utilities;

public class ColorCodes {

	public static String change(String s, Character translate) {
		StringBuilder sb = new StringBuilder(s);
		
		for (int i = 0 ; i < s.length() ; i++) {
			char c = s.charAt(i);
			
			if (c == translate) {
				if ((s.charAt(i + 1) == '0') || (s.charAt(i + 1) == '1') || (s.charAt(i + 1) == '2') || (s.charAt(i + 1) == '3') ||
					(s.charAt(i + 1) == '4') || (s.charAt(i + 1) == '5') || (s.charAt(i + 1) == '6') || (s.charAt(i + 1) == '7') || 
					(s.charAt(i + 1) == '8') || (s.charAt(i + 1) == '9') || (s.charAt(i + 1) == 'a') || (s.charAt(i + 1) == 'b') ||
					(s.charAt(i + 1) == 'c') || (s.charAt(i + 1) == 'd') || (s.charAt(i + 1) == 'e') || (s.charAt(i + 1) == 'f') ||
					(s.charAt(i + 1) == 'l') || (s.charAt(i + 1) == 'm') || (s.charAt(i + 1) == 'o') || (s.charAt(i + 1) == 'n') ||
					(s.charAt(i + 1) == 'r') || (s.charAt(i + 1) == 'k')) {
					
						sb.setCharAt(i, '§');
				}
			}
		}
		
		s = sb.toString();
		
		return s;
	}
}
