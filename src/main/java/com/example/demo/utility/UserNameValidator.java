package com.example.demo.utility;

public class UserNameValidator {
	
	public static boolean isValid(String userName) {
		String pattern = "[a-zA-Z0-9\\._\\-]{3,25}";
		return userName.matches(pattern);
	}

}
