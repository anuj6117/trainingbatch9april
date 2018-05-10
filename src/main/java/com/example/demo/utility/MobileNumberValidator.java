package com.example.demo.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class MobileNumberValidator {
	
	public static boolean isValid(String number)
	{
		Pattern p = Pattern.compile("(0/91)?[0-9][0-9]{9}");
	
		Matcher m = p.matcher(number);
		return (m.find() && m.group().equals(number));
	}
}
