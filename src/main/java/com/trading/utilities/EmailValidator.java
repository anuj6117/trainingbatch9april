package com.trading.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {
	
	public static boolean isValidEmailAddress(String email) {
		      String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

		      Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		      Matcher matcher = pattern.matcher(email);
		      return matcher.matches();
		   }	
	}



