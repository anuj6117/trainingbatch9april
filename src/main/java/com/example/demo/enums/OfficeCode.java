package com.example.demo.enums;

public enum OfficeCode {
	
	Unit1("Unit 951") , Unit2("Unit 159"), Unit3("IRIS");

	private String key;
	
	private OfficeCode(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return this.key;
	}
	
	public static OfficeCode getOfficeCode(String key) {
		for (OfficeCode officeCode : OfficeCode.values()) {
			if (officeCode.getKey().equalsIgnoreCase(key)) {
				return officeCode;
			}
		}
		return null;

	}
	
	public static OfficeCode[] getAllOfficeCode(){
		return OfficeCode.values();
	}
	
	public static OfficeCode getOfficeCodeObject(String key){
		OfficeCode officeCode = null;
		for(OfficeCode keyValue : OfficeCode.values()){
			if(keyValue.getKey().toLowerCase().indexOf(key.toLowerCase()) != -1){
				officeCode = keyValue;
				break;
			}
		}
		return officeCode;
	}
}
