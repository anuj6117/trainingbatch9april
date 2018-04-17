package com.example.demo.enums;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public enum Status 
{
	INACTIVE("inactive"),ACTIVE("active");
	
	private String value;

	public String getValue() {
		return value;
	}

	private Status(String value) {
		this.value = value;
	}
}
