package com.example.demo.utilities;

public class NameValidator {
      public boolean checkNameValidation(String name) {
    	  String pattern = "^[A-Za-z][A-Za-z0-9_-]{3,25}$";
  		return name.matches(pattern);
  	
      }
}
