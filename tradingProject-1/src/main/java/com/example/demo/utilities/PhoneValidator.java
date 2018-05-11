package com.example.demo.utilities;

public class PhoneValidator {
	boolean result =false;
	public boolean checkPhoneNumber(String number) {
		if(number.length()==10 && number.matches("^[0-9]{10}$")) {
			result=true;
		}
		return result;
	}
}
