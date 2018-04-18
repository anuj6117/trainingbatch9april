package com.example.enums;

public enum UserStatus
{
 INACTIVE("Inactive"),ACTIVE("Active");
	public String value; 
	public String getValue()
	{
	 return value;	
	}
	private UserStatus(String value)
	{
		this.value=value;
	}
}

