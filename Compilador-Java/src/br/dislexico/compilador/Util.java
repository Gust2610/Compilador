
package br.dislexico.compilador;

public class Util {
	public static boolean isLetter(char c) {
		int dec = (int) c;

		return (dec > 64 && dec < 91) || (dec > 96 && dec < 123) || (dec == 95);
	}

	public static boolean isDigit(char c) {
		int dec = (int) c;

		return (dec > 47 && dec < 58);
	}

	public static boolean isDot(char c) {
		int dec = (int) c;
		return dec == 46;
	}

	public static boolean isQuote(char c) {
		int dec = (int) c;
		return dec == 39;
	}

	public static boolean isDollar(char c) {
		int dec = (int) c;
		return dec == 36;
	}

	public static boolean isAllNumbersAfterDot(StringBuilder lexema) {
		String s = lexema.toString();
		char[] c = s.toCharArray();
		int dot = 0;
		boolean verif = true;
		boolean entrou = false;
		for (int i = 0; i < c.length; i++) {
			if (c[i] == '.') {
				dot = i;
			}
		}

		for (int i = dot + 1; i < c.length; i++) {
			if ((!Util.isDigit(c[i]))) {
				verif = false;
				entrou = true;
			}
		}

		if (entrou == false) {
			verif = false;
		}
		return verif;
	}
}
