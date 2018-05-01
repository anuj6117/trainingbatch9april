package com.trading.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameValidator {

	public static boolean isValid(String userName) {
	      String regex = "^(?! )[\\w-\\s]*(?<! )$";
	      Pattern ptn = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
	      Matcher mtch = ptn.matcher(userName);
	      return mtch.matches();
	   }	
}
