package com.trainingproject.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.trainingproject.domain.User;

public class ValidationEmail {
	public  static Map<String,String>  validateEmail(User user) {
		Map<String,String> errorStatus=new HashMap<>();
			
			// email validation
			String email = user.getEmail();
			
		     
			if(Pattern.compile("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9]+(\\.[a-z0-9]+)*(\\.[a-z]{2,})$").matcher(email).matches()) {
				errorStatus.put("message","success");
			}
			
			else {
				errorStatus.put("message","Please, enter a valid email address");
			}
			return errorStatus;
	}

}
