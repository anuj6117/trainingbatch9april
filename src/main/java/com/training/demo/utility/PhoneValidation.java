package com.training.demo.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class PhoneValidation {
	
	public static boolean isValid(String number)
	{
		Pattern p = Pattern.compile("(0/91)?[6-9][0-9]{9}");
	
		Matcher m = p.matcher(number);
		return (m.find() && m.group().equals(number));
	}
}

