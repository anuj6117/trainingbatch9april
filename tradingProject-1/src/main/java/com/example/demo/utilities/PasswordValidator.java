package com.example.demo.utilities;

public class PasswordValidator {
	 public  boolean isValid(String password) {
	      String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,32}";
	      return password.matches(pattern);
	   }	
}
