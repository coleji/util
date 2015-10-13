package com.coleji.Util;

public class StringUtil {
	public static String trimLineEndings(String in) {
		char[] charArrForBackwards = in.toCharArray();
		StringBuilder backwards = new StringBuilder();
		boolean atStart = true;
		for (int i=charArrForBackwards.length; i>0; i--) {
			if (atStart && (charArrForBackwards[i-1] == '\r' || charArrForBackwards[i-1] == '\n' || charArrForBackwards[i-1] == ' ')) {
				continue;
			}
			atStart = false;
			backwards.append(charArrForBackwards[i-1]);
		}
		char[] charArrForForwards = backwards.toString().toCharArray();
		StringBuilder forwards = new StringBuilder();
		atStart = true;
		for (int i=charArrForForwards.length; i>0; i--) {
			if (atStart && (charArrForForwards[i-1] == '\r' || charArrForForwards[i-1] == '\n' || charArrForForwards[i-1] == ' ')) {
				continue;
			}
			atStart = false;
			forwards.append(charArrForForwards[i-1]);
		}
		return forwards.toString();
	}
}
