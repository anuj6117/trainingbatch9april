package com.trading.utilities;

public class PasswordValidator {
	public static boolean isValid(String password) {
	      String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*()-.])(?=\\S+$).{8,}";
	      return password.matches(pattern);
	   }	

}
