package com.example.demo.utilities;

public class NameValidator {
      public boolean checkNameValidation(String name) {
    	  String pattern = "[a-zA-Z0-9\\._\\-]{3,25}";
  		return name.matches(pattern);
  	
      }
}
