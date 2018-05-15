package com.example.demo.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

	public EmailValidator() 
	{ }
	
	public boolean checkEmail(String email)
	{
		Pattern pattern = Pattern.compile("^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$");
         Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}		
}
