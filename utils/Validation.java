package com.trainingproject.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.trainingproject.domain.User;

public class Validation {
	
	
public  static Map<String,String>  validate(User user) {
		Map<String,String> errorStatus=new HashMap<>();
		String userName = user.getUserName();
		if(userName != null && userName != "") {
			if(!(String.valueOf(userName.charAt(0)).equals(" ")) && 
					!(String.valueOf(userName.charAt(userName.length()-1)).equals(" "))) {
			if(Pattern.compile("^[a-z0-9_-]{1,25}$").matcher(userName).matches()) {
				errorStatus.put("message","success"); 
			 }
			else {
				errorStatus.put("message","Maximum character allowed for this field is 25 & userName should be lower case");
			}
	    }
			else {
			errorStatus.put("message","Space is not allowed in trailing and leading postion");
			}	
	   }
			else {
				errorStatus.put("message","User Name can't be null");
			
	}

		/*if(Pattern.compile("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9]+(\\.[a-z0-9]+)*(\\.[a-z]{2,})$").matcher(user.getEmail()).matches()) {
			errorStatus.put("message","success");
		}
		
		else {
			errorStatus.put("message","Please, enter a valid email address");
		}
		

		//password validation
		/*if(Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,32}").matcher(user.getPassword()).matches()) {
			errorStatus.put("message","success");
			
		}
		else {
			errorStatus.put("message","Please, enter password with minimum 8 characters. your password should have atleast 1 Upper Case, 1 Lower Case, 1 Digit & 1 Special Character");
		}
		
		
		//country validation
		if(Pattern.compile("^[A-Za-z0-9]{1,25}$").matcher(user.getCountry()).matches()) {
			errorStatus.put("message","success");
					
		}
		else {
			errorStatus.put("message","Country can not be null");
			
		}
			
		
		
		//phoneNumber validation
		String phoneNumber = user.getPhoneNumber();
		if(Pattern.compile("(^$|[0-9]{10})").matcher(phoneNumber).matches()) {
			errorStatus.put("message","success");
		}
		else {
			errorStatus.put("message","Please, enter a valid mobile number");
			
		}
*/	
	
		return errorStatus;
		
}
	
	
	
	
}
