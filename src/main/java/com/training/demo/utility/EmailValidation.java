package com.training.demo.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class EmailValidation {

	private static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
	private static Pattern pattern;
	private Matcher matcher;
	
	public EmailValidation() {
		
		pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
	}
	
	public boolean validateEmail(String email) {
		matcher = pattern.matcher(email);
		return matcher.matches();
	}
}